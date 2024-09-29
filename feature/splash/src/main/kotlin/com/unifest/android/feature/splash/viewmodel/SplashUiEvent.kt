package com.unifest.android.feature.splash.viewmodel

sealed interface SplashUiEvent {
    // data object NavigateToIntro : SplashUiEvent
    data object NavigateToMain : SplashUiEvent
    data object NavigateToPlayStore : SplashUiEvent
    data object CloseApp : SplashUiEvent
}
