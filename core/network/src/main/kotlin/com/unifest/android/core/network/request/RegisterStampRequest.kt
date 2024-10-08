package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterStampRequest(
    @SerialName("token")
    val token: String,
    @SerialName("boothId")
    val boothId: Long,
)
