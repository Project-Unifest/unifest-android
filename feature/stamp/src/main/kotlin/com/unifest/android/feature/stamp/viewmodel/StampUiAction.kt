package com.unifest.android.feature.stamp.viewmodel

import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.model.StampFestivalModel

sealed interface StampUiAction {
    data object OnReceiveStampClick : StampUiAction
    data object OnFindStampBoothClick : StampUiAction
    data object OnRefreshClick : StampUiAction
    data class OnPermissionDialogButtonClick(val buttonType: PermissionDialogButtonType) : StampUiAction
    data object OnDismiss : StampUiAction
    data class OnStampBoothItemClick(val boothId: Long) : StampUiAction
    data object OnDropDownMenuClick : StampUiAction
    data object OnDropDownMenuDismiss : StampUiAction
    data class OnFestivalSelect(val festival: StampFestivalModel) : StampUiAction
    data class OnRetryClick(val error: ErrorType) : StampUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
