package com.unifest.android.feature.stamp.viewmodel.qrscan

sealed interface QRScanUiAction {
    data object OnBackClick : QRScanUiAction
}
