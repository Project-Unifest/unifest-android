package com.unifest.android.feature.intro.viewmodel

sealed interface IntroUiEvent {
    data object NavigateToMain : IntroUiEvent
}
