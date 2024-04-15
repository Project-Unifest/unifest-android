package com.unifest.android.feature.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.domain.entity.Festival
import com.unifest.android.core.domain.entity.FestivalEventEntity
import com.unifest.android.core.domain.entity.IncomingFestivalEventEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

data class HomeUiState(
    val incomingEvents: ImmutableList<IncomingFestivalEventEntity> = persistentListOf(),
    val festivalEvents: ImmutableList<FestivalEventEntity> = persistentListOf(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val interestedFestivals: MutableList<Festival> = mutableListOf(),
    val festivalSearchResults: ImmutableList<Festival> = persistentListOf(),
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isInterestedFestivalDeleteDialogVisible: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
)
