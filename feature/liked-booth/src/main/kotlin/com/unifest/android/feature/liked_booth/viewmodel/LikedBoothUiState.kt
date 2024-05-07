package com.unifest.android.feature.liked_booth.viewmodel

import com.unifest.android.core.model.BoothModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class LikedBoothUiState(
    val isLoading: Boolean = false,
    val likedBooths: ImmutableList<BoothModel> = persistentListOf(),
    // val likedBoothList: ImmutableList<BoothDetailModel> = persistentListOf(),
    val isServerErrorDialogVisible: Boolean = false,
    val isNetworkErrorDialogVisible: Boolean = false,
)
