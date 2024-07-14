package com.unifest.android.feature.waiting.viewmodel

sealed interface WaitingUiEvent {
    data object NavigateBack : WaitingUiEvent
}
