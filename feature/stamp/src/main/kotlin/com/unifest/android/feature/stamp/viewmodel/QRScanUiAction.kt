package com.unifest.android.feature.stamp.viewmodel

sealed interface QRScanUiAction {
    data class OnQRCodeScanned(val boothId: Long) : QRScanUiAction
    data object OnBackClick : QRScanUiAction
}
