package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.domain.entity.Festival
import com.unifest.android.feature.map.model.BoothDetailModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MapUiState(
    val selectedSchoolName: String = "",
    val booths: ImmutableList<BoothDetailModel> = persistentListOf(),
    val selectedBooths: ImmutableList<BoothDetailModel> = persistentListOf(),
    val boothSearchText: TextFieldValue = TextFieldValue(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val interestedFestivals: MutableList<Festival> = mutableListOf(),
    val festivalSearchResults: ImmutableList<Festival> = persistentListOf(),
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isPopularMode: Boolean = false,
    val isBoothSelectionMode: Boolean = false,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isInterestedFestivalDeleteDialogVisible: Boolean = false,
)
