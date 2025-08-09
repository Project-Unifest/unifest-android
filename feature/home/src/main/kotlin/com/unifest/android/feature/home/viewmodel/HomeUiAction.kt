package com.unifest.android.feature.home.viewmodel

import com.unifest.android.core.model.CardNewsModel
import java.time.LocalDate

sealed interface HomeUiAction {
    data class OnDateSelected(val date: LocalDate) : HomeUiAction
    // data class OnToggleStarImageClick(val scheduleIndex: Int, val starIndex: Int, val flag: Boolean) : HomeUiAction

    data class OnStarImageClick(val scheduleIndex: Int, val starIndex: Int) : HomeUiAction
    data object OnStarImageDialogDismiss : HomeUiAction
    data object OnClickWeekMode : HomeUiAction
    data class OnCardNewsClick(val selectedCardNews: CardNewsModel) : HomeUiAction
}
