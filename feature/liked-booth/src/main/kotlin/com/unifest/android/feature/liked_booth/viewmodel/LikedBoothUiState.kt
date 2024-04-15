package com.unifest.android.feature.liked_booth.viewmodel

import com.unifest.android.core.model.BoothDetailModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class LikedBoothUiState(
    val isLoading: Boolean = false,
    val likedBoothList: ImmutableList<BoothDetailModel> = persistentListOf(),
)
