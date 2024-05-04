package com.unifest.android.feature.map.viewmodel

import com.unifest.android.core.common.UiText

sealed interface MapUiEvent {
    data object RequestLocationPermission : MapUiEvent
    data object GoToAppSettings : MapUiEvent
    data class NavigateToBoothDetail(val boothId: Long) : MapUiEvent
    data class ShowSnackBar(val message: UiText) : MapUiEvent
    data class ShowToast(val message: UiText): MapUiEvent
}
