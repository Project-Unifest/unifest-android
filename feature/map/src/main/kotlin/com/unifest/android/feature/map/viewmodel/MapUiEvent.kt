package com.unifest.android.feature.map.viewmodel

sealed interface MapUiEvent {
    data class NavigateToBoothDetail(val boothId: Long) : MapUiEvent
}
