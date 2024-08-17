package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckPinValidationRequest(
    @SerialName("boothId")
    val boothId: Long,
    @SerialName("pinNumber")
    val pinNumber: String,
)
