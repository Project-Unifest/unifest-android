package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.FestivalEventModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.IncomingFestivalEventModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

data class HomeUiState(
    val incomingEvents: ImmutableList<IncomingFestivalEventModel> = persistentListOf(),
    val festivalEvents: ImmutableList<FestivalEventModel> = persistentListOf(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val likedFestivals: MutableList<FestivalModel> = mutableListOf(),
    val festivalSearchResults: ImmutableList<FestivalModel> = persistentListOf(),
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isLikedFestivalDeleteDialogVisible: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
)
