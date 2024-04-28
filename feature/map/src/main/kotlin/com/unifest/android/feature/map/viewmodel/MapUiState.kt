package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.map.model.BoothDetailMapModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MapUiState(
    val selectedSchoolName: String = "",
    val festivalId: Long = 0L,
    val festivalList: ImmutableList<FestivalModel> = persistentListOf(),
    val boothList: ImmutableList<BoothDetailMapModel> = persistentListOf(),
    val popularBoothList: ImmutableList<BoothDetailModel> = persistentListOf(),
    val selectedBoothList: ImmutableList<BoothDetailMapModel> = persistentListOf(),
    val boothSearchText: TextFieldValue = TextFieldValue(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val likedFestivals: MutableList<FestivalModel> = mutableListOf(),
    val festivalSearchResults: ImmutableList<FestivalModel> = persistentListOf(),
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isPopularMode: Boolean = false,
    val isBoothSelectionMode: Boolean = false,
    val isMapOnboardingCompleted: Boolean = false,
    val isFestivalOnboardingCompleted: Boolean = true,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isLikedFestivalDeleteDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isPermissionDialogVisible: Boolean = false,
)
