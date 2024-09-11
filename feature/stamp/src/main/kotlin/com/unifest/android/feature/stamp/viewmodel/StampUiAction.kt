package com.unifest.android.feature.stamp.viewmodel

sealed interface StampUiAction {
    data object OnReceiveStampClick: StampUiAction
    data object OnFindStampBoothClick: StampUiAction
    data object OnRefreshClick : StampUiAction
}
