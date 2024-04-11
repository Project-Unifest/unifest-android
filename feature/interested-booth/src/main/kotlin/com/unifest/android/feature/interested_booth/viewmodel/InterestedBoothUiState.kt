package com.unifest.android.feature.interested_booth.viewmodel

import com.unifest.android.core.domain.entity.BoothDetailEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class InterestedBoothUiState(
    val isLoading: Boolean = false,
    val interestedBooths: ImmutableList<BoothDetailEntity> = persistentListOf(),
)
