package com.unifest.android.feature.intro.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.unifest.android.core.domain.entity.Festival
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalFoundationApi::class)
data class IntroUiState(
    val searchText: TextFieldState = TextFieldState(""),
    val schools: ImmutableList<Festival> = persistentListOf(),
)
