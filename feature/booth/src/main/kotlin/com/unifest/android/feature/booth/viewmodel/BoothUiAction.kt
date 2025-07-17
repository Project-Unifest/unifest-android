package com.unifest.android.feature.booth.viewmodel

sealed interface BoothUiAction {
    data object OnWaitingCheckBoxClick : BoothUiAction
    data class OnBoothItemClick(val boothId: Long) : BoothUiAction
    data class OnRetryClick(val error: ErrorType) : BoothUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
