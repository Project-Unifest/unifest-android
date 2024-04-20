package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.feature.map.model.BoothDetailMapModel

sealed interface MapUiAction {
    data object OnTitleClick : MapUiAction
    data object OnTooltipClick : MapUiAction
    data class OnBoothSearchTextUpdated(val text: TextFieldValue) : MapUiAction
    data object OnBoothSearchTextCleared : MapUiAction
    data class OnBoothMarkerClick(val booths: List<BoothDetailMapModel>) : MapUiAction
    data object OnTogglePopularBooth : MapUiAction
    data class OnBoothItemClick(val boothId: Long) : MapUiAction
    data class OnRetryClick(val error: Error) : MapUiAction
}

enum class Error {
    NETWORK,
    SERVER,
}
