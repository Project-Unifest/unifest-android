package com.unifest.android.feature.liked_booth.viewmodel

import com.unifest.android.core.common.UiText

interface LikedBoothUiEvent {
    data object NavigateBack : LikedBoothUiEvent
    data class NavigateToBoothDetail(val boothId: Long) : LikedBoothUiEvent
    data class ShowSnackBar(val message: UiText) : LikedBoothUiEvent
}
