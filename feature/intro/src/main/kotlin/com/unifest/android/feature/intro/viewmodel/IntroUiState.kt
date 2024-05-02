package com.unifest.android.feature.intro.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class IntroUiState(
    val isLoading: Boolean = false,
    val searchText: TextFieldValue = TextFieldValue(),
    val festivals: ImmutableList<FestivalModel> = persistentListOf(),
    val selectedFestivals: PersistentList<FestivalModel> = persistentListOf(),
    val selectedRegion: String = "전체",
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
