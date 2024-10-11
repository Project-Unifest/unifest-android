package com.unifest.android.feature.stamp.viewmodel

data class QRScanUiState(
    val festivalName: String = "",
    val boothId: Long = 0L,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
