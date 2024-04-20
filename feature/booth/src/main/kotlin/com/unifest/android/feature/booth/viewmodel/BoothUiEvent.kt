package com.unifest.android.feature.booth.viewmodel

sealed interface BoothUiEvent {
    data object NavigateBack : BoothUiEvent
    data object NavigateToBoothLocation : BoothUiEvent
}
