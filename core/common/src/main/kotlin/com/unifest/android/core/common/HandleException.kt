package com.unifest.android.core.common

import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ErrorHandlerActions {
    fun openServerErrorDialog()
    fun openNetworkErrorDialog()
}

fun handleException(exception: Throwable, actions: ErrorHandlerActions) {
    when (exception) {
        is HttpException -> {
            if (exception.code() in 500..511) {
                actions.openServerErrorDialog()
            } else {
                Timber.e(exception)
            }
        }

        is UnknownHostException -> {
            actions.openNetworkErrorDialog()
        }

        is SocketTimeoutException -> {
            actions.openServerErrorDialog()
        }

        else -> {
            Timber.e(exception)
        }
    }
}
