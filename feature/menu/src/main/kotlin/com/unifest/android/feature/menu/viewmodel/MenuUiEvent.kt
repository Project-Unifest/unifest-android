package com.unifest.android.feature.menu.viewmodel

import com.unifest.android.core.common.UiText

sealed interface MenuUiEvent {
    data object NavigateToLikedBooth : MenuUiEvent
    data class NavigateToBoothDetail(val boothId: Long) : MenuUiEvent
    data object NavigateToContact : MenuUiEvent
    data class ShowSnackBar(val message: UiText) : MenuUiEvent
}
