package com.unifest.android.feature.menu.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.BoothDetail
import com.unifest.android.core.model.Festival
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MenuUiState(
    val festivals: ImmutableList<Festival> = persistentListOf(),
    val likedBoothList: ImmutableList<BoothDetail> = persistentListOf(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val likedFestivals: MutableList<Festival> = mutableListOf(),
    val festivalSearchResults: ImmutableList<Festival> = persistentListOf(),
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isLikedFestivalDeleteDialogVisible: Boolean = false,
)
