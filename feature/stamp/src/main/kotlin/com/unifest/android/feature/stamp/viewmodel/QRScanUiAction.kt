package com.unifest.android.feature.stamp.viewmodel

sealed interface QRScanUiAction {
    data object OnBackClick : QRScanUiAction
}
