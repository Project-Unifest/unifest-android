package com.unifest.android.feature.liked_booth.viewmodel

import com.unifest.android.core.model.BoothModel

interface LikedBoothUiAction {
    data object OnBackClick : LikedBoothUiAction
    data class OnLikedBoothItemClick(val boothId: Long) : LikedBoothUiAction
    data class OnToggleBookmark(val booth: BoothModel) : LikedBoothUiAction
    data class OnRetryClick(val error: ErrorType) : LikedBoothUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
