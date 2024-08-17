package com.unifest.android.feature.waiting.viewmodel

sealed interface WaitingUiAction {
    data object OnCancelWaitingClick : WaitingUiAction
    data object OnCheckBoothClick : WaitingUiAction
    data object OnPullToRefresh : WaitingUiAction
}
