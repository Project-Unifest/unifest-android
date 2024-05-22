package com.unifest.android.feature.festival.viewmodel

import com.unifest.android.core.common.UiText

sealed interface FestivalUiEvent {
    data class ShowSnackBar(val message: UiText) : FestivalUiEvent
    data class ShowToast(val message: UiText) : FestivalUiEvent
}
