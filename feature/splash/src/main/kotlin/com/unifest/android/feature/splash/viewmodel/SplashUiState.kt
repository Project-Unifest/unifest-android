package com.unifest.android.feature.splash.viewmodel

data class SplashUiState(
    val isLoading: Boolean = true,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
)
