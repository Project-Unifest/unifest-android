package com.unifest.android.core.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.OpenableColumns
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import kotlin.math.roundToInt

@Suppress("TooGenericExceptionCaught")
class FileUtil @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    /**
     * 서버에 업로드하는 형식의 파일로 converting 하여 리턴
     * 이미지 파일인 경우 -> jpeg 로 반환됨
     * gif 인 경우 -> gif 로 반환됨
     * 파일을 사용 후 반드시 지워줘야 함.
     * **/
    suspend fun getFileForUploadImageFormat(context: Context, uri: Uri?): File? = withContext(Dispatchers.IO) {
        if (uri == null) {
            return@withContext null
        }

        try {
            val file = mkTmpFileFromUri(context, uri) ?: return@withContext null

            return@withContext when (file.extension) {
                "gif" -> {
                    file
                }

                else -> {
                    convertToJPEGFile(file)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    /**
     * 지정된 'Uri'에서 임시 파일을 생성
     * context.contentResolver 를 사용하여 입력 스트림을 가져옴
     * 파일 확장자는 Uri에서 추출됩니다.
     * 임시 파일은 JVM이 종료될 때 삭제됨. 그러나 이 메서드는 파일이 삭제되지 않을 때를 대비하여 수동 삭제 로직도 필요함
     */
    private fun mkTmpFileFromUri(context: Context, uri: Uri?): File? {
        if (uri == null) {
            return null
        }

        var inputStream: InputStream? = null
        var fileOutputStream: FileOutputStream? = null

        try {
            inputStream = context.contentResolver.openInputStream(uri)
            val extension = getFileName(context, uri).substringAfterLast(".", "")

            // 파일이 생성되는 경로는 context의 CacheDir
            val tmpFile = File.createTempFile(inputStream.hashCode().toString(), ".$extension")

            // Jvm이 종료될 때 파일을 삭제하는 메서드지만, 앱이 갑작스럽게 종료될 때 파일이 삭제되지 않음.
            // 수동으로 파일을 삭제하는 로직을 추가적으로 넣어줘야 함.
            tmpFile.deleteOnExit()

            fileOutputStream = FileOutputStream(tmpFile)
            fileOutputStream.write(inputStream?.readBytes())

            return tmpFile
        } catch (e: Exception) {
            Timber.e(e)
            return null
        } finally {
            inputStream?.close()
            fileOutputStream?.close()
        }
    }

    /**
     * 주어진 파일 이름을 변경
     */
    private suspend fun renameFile(file: File?, newName: String): File? = withContext(Dispatchers.IO) {
        file ?: return@withContext null

        try {
            File(file.parent?.toString(), newName).also {
                file.renameTo(it)
                file.delete()
                return@withContext it
            }
        } catch (e: Exception) {
            Timber.e(e)
            return@withContext null
        }
    }

    /**
     * 파일의 MIME 유형을 반환
     */
    fun getMimeType(file: File?): String? {
        file ?: return null
        val fileInputStream = FileInputStream(file)

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(fileInputStream, null, options)

        val mimeType = options.outMimeType ?: return null
        return mimeType.split("/")[1]
    }

    /**
     * 지정된 파일의 이미지를 JPEG 형식으로 변환
     * EXIF 메타데이터를 사용하여 이미지의 방향 정보를 가져옴
     * 필요한 경우 이미지를 회전
     */
    private suspend fun convertToJPEGFile(file: File?): File? = withContext(Dispatchers.IO) {
        if (file == null) {
            return@withContext null
        }

        try {
            val exif = ExifInterface(file.absolutePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED,
            )

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }

            val bitmap = BitmapFactory.decodeFile(file.absolutePath, BitmapFactory.Options())

            val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

            val out = FileOutputStream(file)
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()

            return@withContext renameFile(file, file.nameWithoutExtension + ".jpeg")
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

    /**
     * 파일의 크기를 문자열 형식(B, KB, MB)으로 반환
     */
    fun getFileSize(file: File?): String? {
        val fileSize = file?.length()?.toDouble() ?: return null
        val num: Double
        val suffix: String

        if (fileSize > 1024 * 1024) {
            suffix = "MB"
            num = fileSize / (1024 * 1024)
        } else if (fileSize > 1024) {
            suffix = "KB"
            num = fileSize / 1024
        } else {
            suffix = "B"
            num = fileSize
        }

        return "${(num * 100).roundToInt() / 100.0}$suffix"
    }

    /**
     * 주어진 Uri에서 파일 이름을 추출
     */
    private fun getFileName(context: Context, uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    require(index != -1) { "Column index not found in query result." }
                    result = it.getString(index)
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut?.plus(1) ?: 0)
            }
        }
        return result ?: ""
    }
}
