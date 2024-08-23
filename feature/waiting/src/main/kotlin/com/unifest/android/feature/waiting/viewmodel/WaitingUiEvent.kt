package com.unifest.android.feature.waiting.viewmodel

sealed interface WaitingUiEvent {
    data object NavigateBack : WaitingUiEvent
    data object NavigateToMap : WaitingUiEvent
    data class NavigateToBoothDetail(val boothId: Long) : WaitingUiEvent
}
