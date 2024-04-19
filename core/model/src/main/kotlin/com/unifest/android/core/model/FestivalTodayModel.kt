package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class FestivalTodayModel(
    val thumbnail: String,
    val schoolId: Int,
    val festivalId: Int,
    val date: String,
    val festivalName: String,
    val schoolName: String,
    val starList: List<StarListModel>,
)
@Stable
data class StarListModel(
    val name: String,
    val img: String,
)
