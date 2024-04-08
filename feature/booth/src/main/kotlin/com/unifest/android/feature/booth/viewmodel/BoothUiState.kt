package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.BoothSpot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BoothUiState(
    val boothDetailInfo: BoothDetailEntity = BoothDetailEntity(),
    val bookmarkCount: Int = 0,
    val isBookmarked: Boolean = false,
    val boothSpots: ImmutableList<BoothSpot> = persistentListOf(),
)
