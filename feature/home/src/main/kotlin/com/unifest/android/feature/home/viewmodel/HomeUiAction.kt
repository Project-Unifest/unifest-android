package com.unifest.android.feature.home.viewmodel

import com.unifest.android.core.model.FestivalTodayModel
import java.time.LocalDate

sealed interface HomeUiAction {
    data class OnDateSelected(val date: LocalDate) : HomeUiAction
    data class OnAddAsLikedFestivalClick(val festivalTodayModel: FestivalTodayModel) : HomeUiAction
    data object OnAddLikedFestivalClick : HomeUiAction
    data class OnStarImageClick(val index: Int) : HomeUiAction
    data class OnStarImageDismiss(val index: Int) : HomeUiAction
}
