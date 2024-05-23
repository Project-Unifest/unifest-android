package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.model.MenuModel

sealed interface BoothUiAction {
    data object OnBackClick : BoothUiAction
    data object OnCheckLocationClick : BoothUiAction
    data object OnToggleBookmark : BoothUiAction
    data class OnRetryClick(val error: ErrorType) : BoothUiAction
    data class OnMenuImageClick(val menu: MenuModel) : BoothUiAction
    data object OnMenuImageDialogDismiss : BoothUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
