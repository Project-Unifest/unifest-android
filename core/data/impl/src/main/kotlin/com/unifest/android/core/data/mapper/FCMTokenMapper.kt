package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.FCMTokenModel
import com.unifest.android.core.network.response.FCMTokenResponse

internal fun FCMTokenResponse.toModel(): FCMTokenModel {
    return FCMTokenModel(
        code = code,
        message = message,
        data = data,
    )
}
