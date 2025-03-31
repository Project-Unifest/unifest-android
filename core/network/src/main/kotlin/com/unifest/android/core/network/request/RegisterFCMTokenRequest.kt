package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterFCMTokenRequest(
    @SerialName("deviceId")
    val deviceId: String,
    @SerialName("fcmToken")
    val fcmToken: String,
)
