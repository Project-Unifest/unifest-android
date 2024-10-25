package com.unifest.android.feature.waiting.viewmodel

sealed interface WaitingUiAction {
    data class OnCancelWaitingClick(val waitingId: Long) : WaitingUiAction
    data class OnCancelNoShowWaitingClick(val waitingId: Long) : WaitingUiAction
    data class OnCheckBoothDetailClick(val boothId: Long) : WaitingUiAction
    data object OnWaitingCancelDialogCancelClick : WaitingUiAction
    data object OnWaitingCancelDialogConfirmClick : WaitingUiAction
    data object OnNoShowWaitingCancelDialogCancelClick : WaitingUiAction
    data object OnNoShowWaitingCancelDialogConfirmClick : WaitingUiAction
    data object OnLookForBoothClick : WaitingUiAction
    data object OnRefresh : WaitingUiAction
    //    data object OnPullToRefresh : WaitingUiAction
}
