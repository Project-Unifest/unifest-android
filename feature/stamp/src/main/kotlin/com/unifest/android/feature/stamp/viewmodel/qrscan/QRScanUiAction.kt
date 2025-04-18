package com.unifest.android.feature.stamp.viewmodel.qrscan

sealed interface QRScanUiAction {
    data class OnQRCodeScanned(val boothId: Long) : QRScanUiAction
    data object OnBackClick : QRScanUiAction
}
