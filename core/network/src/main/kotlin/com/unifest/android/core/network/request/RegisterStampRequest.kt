package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterStampRequest(
    @SerialName("deviceId")
    val deviceId: String,
    @SerialName("boothId")
    val boothId: Long,
    @SerialName("festivalId")
    val festivalId: Long,
)
