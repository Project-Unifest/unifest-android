package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class HomeInfoModel(
    val homeCardList: List<HomeCardModel> = emptyList(),
    val homeTipList: List<HomeTipModel> = emptyList(),
)

@Stable
data class HomeCardModel(
    val id: Long = 0L,
    val thumbnailImgUrl: String = "",
    val detailImgUrl: String = "",
)

@Stable
data class HomeTipModel(
    val id: Long = 0L,
    val tipContent: String = "",
)
