package com.unifest.android.feature.intro.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.unifest.android.core.model.FestivalModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class IntroUiState(
    val searchText: TextFieldValue = TextFieldValue(),
    val festivals: ImmutableList<FestivalModel> = persistentListOf(),
    val selectedFestivals: List<FestivalModel> = emptyList(),
)
