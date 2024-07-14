package com.unifest.android.feature.waiting.viewmodel

sealed interface WaitingUiAction {
    data object OnDismiss : WaitingUiAction
}
