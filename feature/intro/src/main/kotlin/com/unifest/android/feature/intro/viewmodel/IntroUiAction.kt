package com.unifest.android.feature.intro.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.FestivalModel

sealed interface IntroUiAction {
    data class OnSearchTextUpdated(val text: TextFieldValue) : IntroUiAction
    data object OnSearchTextCleared : IntroUiAction
    data object OnClearSelectionClick : IntroUiAction
    data class OnFestivalSelected(val festival: FestivalModel) : IntroUiAction
    data class OnFestivalDeselected(val festival: FestivalModel) : IntroUiAction
    data object OnAddCompleteClick : IntroUiAction
}
