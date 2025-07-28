package com.unifest.android.feature.home.viewmodel

import androidx.compose.foundation.text.input.TextFieldState
import com.unifest.android.core.model.CardNewsModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.StarInfoModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

data class HomeUiState(
    val incomingFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val todayFestivals: ImmutableList<FestivalTodayModel> = persistentListOf(),
    val allFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val festivalSearchText: TextFieldState = TextFieldState(),
    val likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val deleteSelectedFestival: FestivalModel? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isStarImageClicked: ImmutableList<ImmutableList<Boolean>> = persistentListOf(persistentListOf()),
    val isWeekMode: Boolean = true,
    val isDataReady: Boolean = true,
    val isStarImageDialogVisible: Boolean = false,
    val selectedStar: StarInfoModel? = null,
    val cardNews: ImmutableList<CardNewsModel> = persistentListOf(),
)
