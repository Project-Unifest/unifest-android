package com.unifest.android.feature.festival.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.designsystem.R as designR
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class FestivalUiState(
    val festivals: ImmutableList<FestivalModel> = persistentListOf(),
    val festivalSearchText: TextFieldValue = TextFieldValue(),
    val searchTextHintRes: Int = designR.string.festival_search_text_field_hint,
    val likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val festivalSearchResults: ImmutableList<FestivalModel> = persistentListOf(),
    val deleteSelectedFestival: FestivalModel? = null,
    val isSearchMode: Boolean = false,
    val isEditMode: Boolean = false,
    val isFestivalSearchBottomSheetVisible: Boolean = false,
    val isLikedFestivalDeleteDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
    val isFestivalOnboardingCompleted: Boolean = false,
    val isNotificationPermissionDialogVisible: Boolean = false,
)
