package com.unifest.android.feature.intro.viewmodel

sealed interface IntroUiEvent {
    data object NavigateToMain : IntroUiEvent
    data object NavigateToPlayStore: IntroUiEvent
    data object CloseApp: IntroUiEvent
}
