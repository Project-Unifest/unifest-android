package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.common.UiText

sealed interface QRScanUiEvent {
    data object NavigateBack : QRScanUiEvent
    data object ScanSuccess : QRScanUiEvent
    data class ScanError(val errorType: QRErrorType) : QRScanUiEvent
    data class ShowToast(val text: UiText) : QRScanUiEvent
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
