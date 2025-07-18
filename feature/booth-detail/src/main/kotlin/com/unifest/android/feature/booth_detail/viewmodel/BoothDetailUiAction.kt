package com.unifest.android.feature.booth_detail.viewmodel

import com.unifest.android.core.common.PermissionDialogButtonType
import com.unifest.android.core.model.MenuModel

sealed interface BoothDetailUiAction {
    data object OnBackClick : BoothDetailUiAction
    data object OnCheckLocationClick : BoothDetailUiAction
    data object OnToggleBookmark : BoothDetailUiAction
    data class OnRetryClick(val error: ErrorType) : BoothDetailUiAction
    data class OnMenuImageClick(val menu: MenuModel) : BoothDetailUiAction
    data object OnMenuImageDialogDismiss : BoothDetailUiAction
    data object OnWaitingButtonClick : BoothDetailUiAction
    data object OnDialogPinButtonClick : BoothDetailUiAction
    data object OnDialogWaitingButtonClick : BoothDetailUiAction
    data object OnWaitingDialogDismiss : BoothDetailUiAction
    data object OnConfirmDialogDismiss : BoothDetailUiAction
    data object OnPinDialogDismiss : BoothDetailUiAction
    data object OnWaitingMinusClick : BoothDetailUiAction
    data object OnWaitingPlusClick : BoothDetailUiAction
    data object OnPolicyCheckBoxClick : BoothDetailUiAction
    data object OnPrivatePolicyClick : BoothDetailUiAction
    data object OnThirdPartyPolicyClick : BoothDetailUiAction
    data object OnScheduleToggleClick : BoothDetailUiAction
    data object OnMoveClick : BoothDetailUiAction
    data object OnNoShowDialogCancelClick : BoothDetailUiAction
    data object OnRequestLocationPermission : BoothDetailUiAction
    data object OnRequestNotificationPermission : BoothDetailUiAction
    data class OnPermissionDialogButtonClick(
        val buttonType: PermissionDialogButtonType,
        val permission: String,
    ) : BoothDetailUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
