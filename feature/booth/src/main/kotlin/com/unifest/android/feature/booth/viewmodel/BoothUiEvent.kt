package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.common.UiText

sealed interface BoothUiEvent {
    data object NavigateBack : BoothUiEvent
    data object NavigateToBoothLocation : BoothUiEvent
    data object NavigateToPrivatePolicy : BoothUiEvent
    data object NavigateToThirdPartyPolicy : BoothUiEvent
    data object NavigateToAppSetting : BoothUiEvent
    data object NavigateToWaiting : BoothUiEvent
    data class ShowSnackBar(val message: UiText) : BoothUiEvent
    data class ShowToast(val message: UiText) : BoothUiEvent
    data class RequestPermission(val permission: String) : BoothUiEvent
}
