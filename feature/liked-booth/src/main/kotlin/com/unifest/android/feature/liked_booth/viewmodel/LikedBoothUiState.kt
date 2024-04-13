package com.unifest.android.feature.liked_booth.viewmodel

import com.unifest.android.core.domain.entity.BoothDetailEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class LikedBoothUiState(
    val isLoading: Boolean = false,
    val likedBoothList: ImmutableList<BoothDetailEntity> = persistentListOf(),
)
