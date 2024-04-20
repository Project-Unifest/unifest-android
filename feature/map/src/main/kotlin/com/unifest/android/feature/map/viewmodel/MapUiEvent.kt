package com.unifest.android.feature.map.viewmodel

sealed interface MapUiEvent {
    data class NavigateToBooth(val boothId: Long) : MapUiEvent
}
