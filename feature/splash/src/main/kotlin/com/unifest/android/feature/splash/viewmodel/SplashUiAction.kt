package com.unifest.android.feature.splash.viewmodel

sealed interface SplashUiAction {
    data object OnUpdateClick : SplashUiAction
    data object OnUpdateDismissClick : SplashUiAction
    data object OnConfirmClick : SplashUiAction
}
