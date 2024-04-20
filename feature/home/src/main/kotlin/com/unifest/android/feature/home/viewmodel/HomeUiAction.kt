package com.unifest.android.feature.home.viewmodel

import com.unifest.android.core.model.FestivalTodayModel
import java.time.LocalDate

sealed interface HomeUiAction {
    data class OnDateSelected(val date: LocalDate): HomeUiAction
    data class OnAddLikedFestivalClick(val festivalTodayModel: FestivalTodayModel) : HomeUiAction
}
