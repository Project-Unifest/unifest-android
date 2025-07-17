package com.unifest.android.feature.booth.viewmodel

sealed interface BoothUiEvent {
    data class NavigateToBoothDetail(val boothId: Long) : BoothUiEvent
}
