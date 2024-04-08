package com.unifest.android.feature.booth.viewmodel

import com.unifest.android.core.domain.entity.BoothDetailEntity

data class BoothUiState(
    val boothDetailInfo: BoothDetailEntity = BoothDetailEntity(),
    val bookmarkCount: Int = 0,
    val isBookmarked: Boolean = false
) {

}
