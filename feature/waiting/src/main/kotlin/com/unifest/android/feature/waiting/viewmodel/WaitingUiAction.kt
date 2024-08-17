package com.unifest.android.feature.waiting.viewmodel

sealed interface WaitingUiAction {
    data object OnCancelWaitingClick : WaitingUiAction
    data object OnCheckBoothDetailClick : WaitingUiAction
    data object OnPullToRefresh : WaitingUiAction
    data object OnWaitingCancelDialogCancelClick : WaitingUiAction
    data object OnWaitingCancelDialogConfirmClick : WaitingUiAction
}
