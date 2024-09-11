package com.unifest.android.feature.stamp.viewmodel

sealed interface QRScanEvent {
    data object NavigateBack : QRScanEvent
    data object ScanSuccess : QRScanEvent
    data class ScanError(val errorType: QRErrorType) : QRScanEvent
}

data class QRScanException(
    val errorType: QRErrorType?,
) : Exception(errorType?.name)

enum class QRErrorType {
    ShowNotToday, UsedTicket, TicketNotFound;

    companion object {
        fun fromString(type: String?): QRErrorType? = when (type?.trim()?.uppercase()) {
            "SHOW_NOT_TODAY" -> ShowNotToday
            "USED_TICKET" -> UsedTicket
            "TICKET_NOT_FOUND" -> TicketNotFound
            else -> null
        }
    }
}
