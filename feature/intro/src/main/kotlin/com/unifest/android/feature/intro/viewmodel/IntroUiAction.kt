package com.unifest.android.feature.intro.viewmodel

import com.unifest.android.core.model.FestivalModel

sealed interface IntroUiAction {
    data object OnClearSelectionClick : IntroUiAction
    data class OnFestivalSelected(val festival: FestivalModel) : IntroUiAction
    data class OnFestivalDeselected(val festival: FestivalModel) : IntroUiAction
    data object OnAddCompleteClick : IntroUiAction
}
