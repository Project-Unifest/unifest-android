package com.unifest.android.core.common

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private val jsonRule = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
}

interface QRErrorHandlerActions {
    fun setServerErrorDialogVisible(flag: Boolean)
    fun setNetworkErrorDialogVisible(flag: Boolean)
    fun showErrorMessage(message: UiText)
}

fun handleException(exception: Throwable, actions: QRErrorHandlerActions) {
    when (exception) {
        is HttpException -> {
            handleHttpException(exception, actions)
        }

        is UnknownHostException -> {
            actions.setNetworkErrorDialogVisible(true)
        }

        is SocketTimeoutException -> {
            actions.setServerErrorDialogVisible(true)
        }

        else -> {
            Timber.e(exception)
        }
    }
}

private fun handleHttpException(exception: HttpException, actions: QRErrorHandlerActions) {
    when (exception.code()) {
        in 400..499 -> {
            val errorBody = exception.response()?.errorBody()?.string()
            try {
                val errorResponse = errorBody?.let { jsonRule.decodeFromString(ErrorResponse.serializer(), it) }
                if (errorResponse != null && errorResponse.code == "9000") {
                    actions.showErrorMessage(UiText.StringResource(R.string.already_collected_stamp))
                } else {
                    actions.showErrorMessage(UiText.StringResource(R.string.not_found_stamp))
                }
            } catch (e: SerializationException) {
                Timber.e(e)
                actions.showErrorMessage(UiText.StringResource(R.string.not_found_stamp))
            } catch (e: IllegalArgumentException) {
                Timber.e(e)
                actions.showErrorMessage(UiText.StringResource(R.string.not_found_stamp))
            } catch (e: IOException) {
                Timber.e(e)
                actions.showErrorMessage(UiText.StringResource(R.string.not_found_stamp))
            }
        }

        in 500..511 -> actions.setServerErrorDialogVisible(true)
        else -> Timber.e(exception)
    }
}
