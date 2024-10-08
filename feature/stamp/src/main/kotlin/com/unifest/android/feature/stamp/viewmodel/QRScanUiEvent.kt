package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.common.UiText

sealed interface QRScanUiEvent {
    data object NavigateBack : QRScanUiEvent
    data class ScanSuccess(val entryCode: String) : QRScanUiEvent
    data class ScanError(val errorType: QRErrorType) : QRScanUiEvent
    data class ShowToast(val text: UiText) : QRScanUiEvent
}

data class QRScanException(
    val errorType: QRErrorType?,
) : Exception(errorType?.name)

enum class QRErrorType {
    BoothNotToday, UsedStamp, StampNotFound;

    companion object {
        fun fromString(type: String?): QRErrorType? = when (type?.trim()?.uppercase()) {
            "BOOTH_NOT_TODAY" -> BoothNotToday
            "USED_STAMP" -> UsedStamp
            "STAMP_NOT_FOUND" -> StampNotFound
            else -> null
        }
    }
}
