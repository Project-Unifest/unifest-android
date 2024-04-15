package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.model.BoothDetail
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BoothUiState(
    val boothDetailInfo: BoothDetail = BoothDetail(),
    val bookmarkCount: Int = 0,
    val isBookmarked: Boolean = false,
    val boothSpots: ImmutableList<BoothDetail> = persistentListOf(),
)
