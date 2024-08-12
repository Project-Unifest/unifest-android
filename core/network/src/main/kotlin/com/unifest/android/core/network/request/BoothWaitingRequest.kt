package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoothWaitingRequest(
    @SerialName("boothId")
    val boothId: Long,
    @SerialName("tel")
    val tel: String,
    @SerialName("deviceId")
    val deviceId: String,
    @SerialName("partySize")
    val partySize: Long,
    @SerialName("pinNumber")
    val pinNumber: String
)
