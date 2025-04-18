package com.unifest.android.feature.stamp.viewmodel.qrscan

import com.unifest.android.core.common.UiText

sealed interface QRScanUiEvent {
    data object NavigateBack : QRScanUiEvent
    data object RegisterStampCompleted : QRScanUiEvent
    data object RegisterStampFailed : QRScanUiEvent
    data class ScanSuccess(val entryCode: String) : QRScanUiEvent
    data class ShowToast(val text: UiText) : QRScanUiEvent
}
