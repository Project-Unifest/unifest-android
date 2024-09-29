package com.unifest.android.feature.map.viewmodel

import com.unifest.android.core.common.UiText

sealed interface MapUiEvent {
    data object RequestPermissions : MapUiEvent
    data class RequestPermission(val permission: String) : MapUiEvent
    data object NavigateToAppSetting : MapUiEvent
    data class NavigateToBoothDetail(val boothId: Long) : MapUiEvent
    data class ShowSnackBar(val message: UiText) : MapUiEvent
}
