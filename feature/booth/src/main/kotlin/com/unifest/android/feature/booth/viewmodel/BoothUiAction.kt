package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.model.MenuModel

sealed interface BoothUiAction {
    data object OnBackClick : BoothUiAction
    data object OnCheckLocationClick : BoothUiAction
    data object OnToggleBookmark : BoothUiAction
    data class OnRetryClick(val error: ErrorType) : BoothUiAction
    data class OnMenuImageClick(val menu: MenuModel) : BoothUiAction
    data object OnMenuImageDialogDismiss : BoothUiAction
    data object OnWaitingButtonClick : BoothUiAction
    data object OnDialogPinButtonClick : BoothUiAction
    data object OnDialogWaitingButtonClick : BoothUiAction
    data class OnPinNumberUpdated(val pinNumber: String) : BoothUiAction
    data class OnWaitingTelUpdated(val tel: String) : BoothUiAction
    data object OnWaitingDialogDismiss : BoothUiAction
    data object OnConfirmDialogDismiss : BoothUiAction
    data object OnPinDialogDismiss : BoothUiAction
    data object OnWaitingMinusClick : BoothUiAction
    data object OnWaitingPlusClick : BoothUiAction
    data object OnPolicyCheckBoxClick : BoothUiAction
    data object OnPrivatePolicyClick : BoothUiAction
    data object OnThirdPartyPolicyClick : BoothUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
