package com.unifest.android.feature.intro.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.FestivalModel

sealed interface IntroUiAction {
    data class OnSearchTextUpdated(val searchText: TextFieldValue) : IntroUiAction
    data object OnSearchTextCleared : IntroUiAction
    data class OnSearch(val searchText: TextFieldValue) : IntroUiAction
    data class OnRegionTapClicked(val region: String) : IntroUiAction
    data object OnClearSelectionClick : IntroUiAction
    data class OnFestivalSelected(val festival: FestivalModel) : IntroUiAction
    data class OnFestivalDeselected(val festival: FestivalModel) : IntroUiAction
    data object OnAddCompleteClick : IntroUiAction
    data class OnRetryClick(val error: ErrorType) : IntroUiAction
    data object OnUpdateClick : IntroUiAction
    data object OnUpdateDismissClick : IntroUiAction
}

enum class ErrorType {
    NETWORK,
    SERVER,
}
