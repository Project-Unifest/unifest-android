package com.unifest.android.core.common

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.FestivalModel

sealed interface FestivalUiAction {
    data object OnDismiss : FestivalUiAction
    data class OnSearchTextUpdated(val text: TextFieldValue) : FestivalUiAction
    data object OnSearchTextCleared : FestivalUiAction
    data class OnEnableSearchMode(val flag: Boolean) : FestivalUiAction
    data object OnEnableEditMode : FestivalUiAction
    data class OnAddClick(val festival: FestivalModel) : FestivalUiAction
    data object OnDeleteIconClick : FestivalUiAction
    data class OnDialogButtonClick(val type: ButtonType, val festival: FestivalModel? = null) : FestivalUiAction
}

enum class ButtonType {
    CONFIRM,
    CANCEL,
}
