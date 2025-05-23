package com.unifest.android.feature.home.viewmodel

import java.time.LocalDate

sealed interface HomeUiAction {
    data class OnDateSelected(val date: LocalDate) : HomeUiAction
    // data class OnToggleStarImageClick(val scheduleIndex: Int, val starIndex: Int, val flag: Boolean) : HomeUiAction

    data class OnStarImageClick(val scheduleIndex: Int, val starIndex: Int) : HomeUiAction
    data object OnStarImageDialogDismiss : HomeUiAction
    data object OnClickWeekMode : HomeUiAction
}
