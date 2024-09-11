package com.unifest.android.feature.stamp.viewmodel

sealed interface StampUiEvent {
    data object NavigateBack : StampUiEvent
    data object NavigateToQRScan : StampUiEvent
    data object RequestCameraPermission : StampUiEvent
    data object NavigateToAppSetting : StampUiEvent
    data class NavigateToBoothDetail(val boothId: Long) : StampUiEvent
}
