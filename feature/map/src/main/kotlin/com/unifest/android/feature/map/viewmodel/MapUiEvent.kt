package com.unifest.android.feature.map.viewmodel

sealed interface MapUiEvent {
    data object RequestLocationPermission: MapUiEvent
    data object PermissionGranted : MapUiEvent
    data object GoToAppSettings : MapUiEvent
    data class NavigateToBoothDetail(val boothId: Long) : MapUiEvent
}
