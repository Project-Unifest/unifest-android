package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

data class HomeUiState(
    val incomingFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val todayFestivals: ImmutableList<FestivalTodayModel> = persistentListOf(),
    val allFestivals: PersistentList<FestivalModel> = persistentListOf(
        FestivalModel(1, 1, "", "Spring Festival", ",", "2024-04-01", "2024-04-02", 0f, 0f),
        FestivalModel(2, 2, "", "Spring Festival", ",", "2024-04-04", "2024-04-05", 0f, 0f),
        FestivalModel(3, 3, "", "Spring Festival", ",", "2024-04-08", "2024-04-10", 0f, 0f),
    ),
//    val allFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val likedFestivals: MutableList<FestivalModel> = mutableListOf(),
    val festivalSearchResults: ImmutableList<FestivalModel> = persistentListOf(),
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isLikedFestivalDeleteDialogVisible: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val showAddToFavoritesButton: Boolean = false,
)
