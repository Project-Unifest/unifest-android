package com.unifest.android.feature.map.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.BoothSpot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalFoundationApi::class)
data class MapUiState(
    val selectedSchoolName: String = "건국대학교",
    val searchText: TextFieldState = TextFieldState(""),
    val boothSpots: ImmutableList<BoothSpot> = persistentListOf(),
    val boothList: ImmutableList<BoothDetailEntity> = persistentListOf(),
)
