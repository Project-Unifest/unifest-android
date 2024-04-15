package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.model.BoothDetailModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BoothUiState(
    val boothDetailInfo: BoothDetailModel = BoothDetailModel(),
    val bookmarkCount: Int = 0,
    val isBookmarked: Boolean = false,
    val boothSpots: ImmutableList<BoothDetailModel> = persistentListOf(),
)
