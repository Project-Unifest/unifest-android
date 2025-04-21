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
) {
    val waitingStatus: WaitingStatus
        get() = WaitingStatus.fromString(status)
}

@Stable
enum class WaitingStatus(val value: String) {
    RESERVED("RESERVED"),    // 예약됨
    CALLED("CALLED"),      // 호출됨
    COMPLETED("COMPLETED"), // 완료됨
    NOSHOW("NOSHOW"),      // 노쇼
    UNKNOWN("UNKNOWN");    // 알 수 없는 상태

    companion object {
        fun fromString(value: String): WaitingStatus {
            return entries.find { it.value == value } ?: UNKNOWN
        }
    }
}
