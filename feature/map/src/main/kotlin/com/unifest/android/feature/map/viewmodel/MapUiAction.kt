package com.unifest.android.feature.map.viewmodel

import com.unifest.android.feature.map.model.BoothDetailMapModel

sealed interface MapUiAction {
    data object OnTooltipClick : MapUiAction
    data class OnBoothMarkerClick(val booths: List<BoothDetailMapModel>) : MapUiAction
    data object OnTogglePopularBooth : MapUiAction
    data class OnBoothItemClick(val boothId: Long) : MapUiAction
    data class OnRetryClick(val error: Error): MapUiAction
}

enum class Error {
    NETWORK,
    SERVER
}
