package com.unifest.android.feature.booth.viewmodel

sealed interface BoothUiAction {
    data object OnBackClick : BoothUiAction
    data object OnCheckLocationClick : BoothUiAction
    data object OnToggleBookmark : BoothUiAction
    data class OnRetryClick(val error: ErrorType) : BoothUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
