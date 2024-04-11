package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.BoothSpot
import com.unifest.android.core.domain.entity.Festival
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MapUiState(
    val selectedSchoolName: String = "",
    val boothSearchText: TextFieldValue = TextFieldValue(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val boothSpots: ImmutableList<BoothSpot> = persistentListOf(),
    val boothList: ImmutableList<BoothDetailEntity> = persistentListOf(),
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val interestedFestivals: MutableList<Festival> = mutableListOf(),
    val festivalSearchResults: ImmutableList<Festival> = persistentListOf(),
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isPopularMode: Boolean = false,
    val isInterestedFestivalDeleteDialogVisible: Boolean = false,
)
