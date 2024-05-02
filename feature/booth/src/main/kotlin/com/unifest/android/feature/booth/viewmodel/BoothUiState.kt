package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.model.BoothDetailModel

data class BoothUiState(
    val isLoading: Boolean = false,
    val boothDetailInfo: BoothDetailModel = BoothDetailModel(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
