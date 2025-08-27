package com.unifest.android.feature.menu.viewmodel

import com.unifest.android.core.common.UiText

sealed interface MenuUiEvent {
    data object NavigateBack : MenuUiEvent
    data object NavigateToLikedBooth : MenuUiEvent
    data class NavigateToBoothDetail(val boothId: Long) : MenuUiEvent
    data object NavigateToContact : MenuUiEvent
    data object NavigateToAdministratorMode : MenuUiEvent
    data object NavigateToWhoAreUForm : MenuUiEvent
    data class ShowSnackBar(val message: UiText) : MenuUiEvent
    data class ShowToast(val message: UiText) : MenuUiEvent
}
