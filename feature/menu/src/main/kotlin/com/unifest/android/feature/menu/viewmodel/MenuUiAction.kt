package com.unifest.android.feature.menu.viewmodel

import com.unifest.android.core.model.BoothDetailModel

sealed interface MenuUiAction {
    data object OnAddClick: MenuUiAction
    data object OnShowMoreClick: MenuUiAction
    data class OnToggleBookmark(val booth: BoothDetailModel) : MenuUiAction
    data object OnContactClick: MenuUiAction
}
