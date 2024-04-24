package com.unifest.android.feature.menu.viewmodel

import com.unifest.android.core.model.BoothDetailModel

sealed interface MenuUiAction {
    data class OnLikedFestivalItemClick(val schoolName: String) : MenuUiAction
    data object OnAddClick : MenuUiAction
    data object OnShowMoreClick : MenuUiAction
    data class OnLikedBoothItemClick(val boothId: Long) : MenuUiAction
    data class OnToggleBookmark(val booth: BoothDetailModel) : MenuUiAction
    data object OnContactClick : MenuUiAction
}
