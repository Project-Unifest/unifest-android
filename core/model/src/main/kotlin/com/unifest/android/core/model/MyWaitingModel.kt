package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class MyWaitingModel(
    val boothId: Long = 0L,
    val waitingId: Long = 0L,
    val partySize: Long = 0L,
    val tel: String = "",
    val deviceId: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val status: String = "",
    val waitingOrder: Long = 0L,
    val boothName: String = "",
)
