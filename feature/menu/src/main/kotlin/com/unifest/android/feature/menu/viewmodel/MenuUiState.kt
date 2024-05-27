package com.unifest.android.feature.menu.viewmodel

import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.LikedBoothModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MenuUiState(
    val festivals: ImmutableList<FestivalModel> = persistentListOf(),
    val likedBooths: ImmutableList<LikedBoothModel> = persistentListOf(),
    val likedFestivals: ImmutableList<FestivalModel> = persistentListOf(),
    val isNetworkErrorDialogVisible: Boolean = false,
    val isServerErrorDialogVisible: Boolean = false,
)
