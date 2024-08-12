package com.unifest.android.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckPinValidationResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Long,
)

@Serializable
data class WaitingResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Waiting,
)

@Serializable
data class Waiting(
    @SerialName("boothId")
    val boothId: Long,
    @SerialName("waitingId")
    val waitingId: Long,
    @SerialName("partySize")
    val partySize: Long,
    @SerialName("tel")
    val tel: String,
    @SerialName("deviceId")
    val deviceId: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("status")
    val status: String,
    @SerialName("waitingOrder")
    val waitingOrder: Long,
    @SerialName("boothName")
    val boothName: String
)
