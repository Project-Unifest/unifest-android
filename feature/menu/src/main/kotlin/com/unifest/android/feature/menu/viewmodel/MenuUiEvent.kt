package com.unifest.android.feature.menu.viewmodel

sealed interface MenuUiEvent {
    data object NavigateToLikedBooth: MenuUiEvent
    data object NavigateToContact: MenuUiEvent
}
