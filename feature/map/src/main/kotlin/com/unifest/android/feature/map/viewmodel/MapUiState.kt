package com.unifest.android.feature.map.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.AllBoothsModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.feature.map.model.AllBoothsMapModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MapUiState(
    val festivalInfo: FestivalModel = FestivalModel(),
    val festivalList: ImmutableList<FestivalModel> = persistentListOf(),
    val boothList: ImmutableList<AllBoothsMapModel> = persistentListOf(),
    val popularBoothList: ImmutableList<AllBoothsModel> = persistentListOf(),
    val selectedBoothList: ImmutableList<AllBoothsMapModel> = persistentListOf(),
    val boothSearchText: TextFieldValue = TextFieldValue(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val festivalSearchResults: ImmutableList<FestivalModel> = persistentListOf(),
    val selectedBoothTypeChips: ImmutableList<String> = persistentListOf(),
    val filteredBoothsList: ImmutableList<AllBoothsMapModel> = persistentListOf(),
    val deleteSelectedFestival: FestivalModel? = null,
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
