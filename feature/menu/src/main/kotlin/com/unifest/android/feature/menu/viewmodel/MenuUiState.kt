package com.unifest.android.feature.menu.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.Festival
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MenuUiState(
    val festivals: ImmutableList<Festival> = persistentListOf(),
    val interestedBooths: ImmutableList<BoothDetailEntity> = persistentListOf(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val interestedFestivals: MutableList<Festival> = mutableListOf(),
    val festivalSearchResults: ImmutableList<Festival> = persistentListOf(),
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isInterestedFestivalDeleteDialogVisible: Boolean = false,
)
