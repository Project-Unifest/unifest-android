package com.unifest.android.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyWaitingResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<MyWaiting>,
)

@Serializable
data class MyWaiting(
    @SerialName("boothId")
    val boothId: Long = 0L,
    @SerialName("waitingId")
    val waitingId: Long = 0L,
    @SerialName("partySize")
    val partySize: Long = 0L,
    @SerialName("tel")
    val tel: String = "",
    @SerialName("deviceId")
    val deviceId: String = "",
    @SerialName("createdAt")
    val createdAt: String = "",
    @SerialName("updatedAt")
    val updatedAt: String = "",
    @SerialName("status")
    val status: String = "",
    @SerialName("waitingOrder")
    val waitingOrder: Long = 0L,
    @SerialName("boothName")
    val boothName: String = "",
)
