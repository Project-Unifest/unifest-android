package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class FestivalTodayModel(
    val thumbnail: String,
    val schoolId: Long,
    val festivalId: Long,
    val beginDate: String,
    val endDate: String,
    val festivalName: String,
    val schoolName: String,
    val starInfo: List<StarInfoModel>,
)

@Stable
data class StarInfoModel(
    val name: String,
    val imgUrl: String,
)
