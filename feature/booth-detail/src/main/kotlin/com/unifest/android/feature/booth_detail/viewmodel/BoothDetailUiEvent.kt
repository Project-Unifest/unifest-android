package com.unifest.android.feature.booth_detail.viewmodel

import com.unifest.android.core.common.UiText

sealed interface BoothDetailUiEvent {
    data object NavigateBack : BoothDetailUiEvent
    data object NavigateToBoothDetailLocation : BoothDetailUiEvent
    data object NavigateToPrivatePolicy : BoothDetailUiEvent
    data object NavigateToThirdPartyPolicy : BoothDetailUiEvent
    data object NavigateToAppSetting : BoothDetailUiEvent
    data object NavigateToWaiting : BoothDetailUiEvent
    data class ShowSnackBar(val message: UiText) : BoothDetailUiEvent
    data class ShowToast(val message: UiText) : BoothDetailUiEvent
    data object RequestPermission : BoothDetailUiEvent
}
