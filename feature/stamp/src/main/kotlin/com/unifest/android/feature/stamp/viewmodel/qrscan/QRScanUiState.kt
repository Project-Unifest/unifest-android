package com.unifest.android.feature.stamp.viewmodel.qrscan

data class QRScanUiState(
    val isLoading: Boolean = false,
    val festivalName: String = "",
    val boothId: Long = 0L,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
