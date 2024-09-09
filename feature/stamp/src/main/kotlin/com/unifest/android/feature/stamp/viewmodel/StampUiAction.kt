package com.unifest.android.feature.waiting.viewmodel

sealed interface StampUiAction {
    data class OnCheckBoothDetailClick(val boothId: Long) : StampUiAction
    data object OnPullToRefresh : StampUiAction
    data object OnStampCancelDialogCancelClick : StampUiAction
    data object OnLookForBoothClick : StampUiAction
    data object OnRefresh : StampUiAction
}
