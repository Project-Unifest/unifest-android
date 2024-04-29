package com.unifest.android.feature.menu.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MenuUiState(
    val festivals: ImmutableList<FestivalModel> = persistentListOf(),
    val likedBoothList: ImmutableList<BoothDetailModel> = persistentListOf(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val festivalSearchResults: ImmutableList<FestivalModel> = persistentListOf(),
    val deleteSelectedFestival: FestivalModel? = null,
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isLikedFestivalDeleteDialogVisible: Boolean = false,
)
