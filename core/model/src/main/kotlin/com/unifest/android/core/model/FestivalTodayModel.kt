package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class FestivalTodayModel(
    val schoolId: Int = 0,
    val festivalId: Int = 0,
    val date: String = "",
    val festivalName: String = "",
    val schoolName: String = "",
    val starList: List<StarListModel> = emptyList(),
)
@Stable
data class StarListModel(
    val name: String,
    val img: String,
)
