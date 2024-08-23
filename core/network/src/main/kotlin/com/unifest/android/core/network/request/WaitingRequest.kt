package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WaitingRequest(
    @SerialName("waitingId")
    val waitingId: Long,
    @SerialName("deviceId")
    val deviceId: String,
)
