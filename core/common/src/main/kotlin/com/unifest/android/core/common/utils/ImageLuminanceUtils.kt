package com.unifest.android.core.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.get
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

data class LuminanceResult(
    val luminance: Float,
    val isDark: Boolean,
) {
    companion object {
        const val LUMINANCE_THRESHOLD = 0.5f
    }
}

object ImageLuminanceUtils {

    @Suppress("TooGenericExceptionCaught")
    suspend fun calculateLuminance(imageUrl: String, context: Context): LuminanceResult? {
        return withContext(Dispatchers.Default) {
            try {
                val imageLoader = context.imageLoader

                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .allowHardware(false)
                    .build()

                val result = imageLoader.execute(request)

                Timber.d("ImageLoader result: ${result::class.simpleName}")
                if (result is SuccessResult) {
                    val drawable = result.drawable
                    Timber.d("Drawable type: ${drawable::class.simpleName}")

                    val bitmap = when (drawable) {
                        is BitmapDrawable -> {
                            val originalBitmap = drawable.bitmap
                            // HARDWARE bitmap은 pixel 접근이 불가능하므로 SOFTWARE로 복사
                            if (originalBitmap.config == Bitmap.Config.HARDWARE) {
                                originalBitmap.copy(Bitmap.Config.ARGB_8888, false)
                            } else {
                                originalBitmap
                            }
                        }

                        else -> {
                            Timber.d("Drawable is not BitmapDrawable, returning null")
                            return@withContext null
                        }
                    }

                    val luminanceResult = calculateBitmapLuminance(bitmap)
                    Timber.d("Luminance calculation complete: $luminanceResult")
                    luminanceResult
                } else {
                    Timber.d("ImageLoader request failed: $result")
                    null
                }
            } catch (e: Exception) {
                Timber.d("Exception during luminance calculation: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }

    private fun calculateBitmapLuminance(bitmap: Bitmap): LuminanceResult {
        val width = bitmap.width
        val height = bitmap.height
        var totalLuminance = 0.0

        // 상단 30% 영역만 샘플링 (status bar와 top app bar 영역)
        val sampleHeight = (height * 0.3).toInt()
        // 성능을 위한 샘플링 간격(모든 픽셀을 다 검사하지 않고 일정 간격으로만 픽셀을 샘플링해서 계산 속도를 높임)
        val stepX = maxOf(1, width / 50)
        val stepY = maxOf(1, sampleHeight / 20)
        var sampleCount = 0

        for (y in 0 until sampleHeight step stepY) {
            for (x in 0 until width step stepX) {
                val pixel = bitmap[x, y]
                val red = (pixel shr 16) and 0xFF
                val green = (pixel shr 8) and 0xFF
                val blue = pixel and 0xFF

                // ITU-R BT.709 luminance formula
                // 0.2126 (Red): 인간의 눈이 빨간색에 가장 덜 민감
                // 0.7152 (Green): 인간의 눈이 초록색에 가장 민감 (약 71%)
                // 0.0722 (Blue): 인간의 눈이 파란색에 두 번째로 덜 민감
                val luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255.0
                totalLuminance += luminance
                sampleCount++
            }
        }

        val averageLuminance = if (sampleCount > 0) (totalLuminance / sampleCount).toFloat() else 0f
        val isDark = averageLuminance < LuminanceResult.LUMINANCE_THRESHOLD

        return LuminanceResult(averageLuminance, isDark)
    }
}
