package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
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
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val deleteSelectedFestival: FestivalModel? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isStarImageClicked: ImmutableList<ImmutableList<Boolean>> = persistentListOf(persistentListOf()),
    val isWeekMode: Boolean = false,
    val isDataReady: Boolean = true,
    val isStarImageDialogVisible: Boolean = false,
    val selectedStar: StarInfoModel? = null,
)
