package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.common.UiText

sealed interface BoothUiEvent {
    data object NavigateBack : BoothUiEvent
    data object NavigateToBoothLocation : BoothUiEvent
    data class OnShowSnackBar(
        val message: UiText
    ) : BoothUiEvent
}
