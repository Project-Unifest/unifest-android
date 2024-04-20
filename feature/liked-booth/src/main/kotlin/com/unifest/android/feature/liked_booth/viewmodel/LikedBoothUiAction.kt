package com.unifest.android.feature.liked_booth.viewmodel

import com.unifest.android.core.model.BoothDetailModel

interface LikedBoothUiAction {
    data object OnBackClick: LikedBoothUiAction
    data class OnToggleBookmark(val booth: BoothDetailModel): LikedBoothUiAction
}
