package com.unifest.android.feature.menu.viewmodel

import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.LikedBoothModel

sealed interface MenuUiAction {
    data class OnLikedFestivalItemClick(val festival: FestivalModel) : MenuUiAction
    data object OnShowMoreClick : MenuUiAction
    data class OnLikedBoothItemClick(val boothId: Long) : MenuUiAction
    data class OnToggleBookmark(val booth: LikedBoothModel) : MenuUiAction
    data object OnContactClick : MenuUiAction
    data object OnAdministratorModeClick : MenuUiAction
    data class OnRetryClick(val error: ErrorType) : MenuUiAction
    data class OnToggleClustering(val isChecked: Boolean) : MenuUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
