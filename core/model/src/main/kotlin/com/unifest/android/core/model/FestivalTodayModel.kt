package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class FestivalTodayModel(
    val festivalId: Int,
    val date: String,
    val festivalName: String,
    val schoolName: String,
    val starList: List<Celebrity>,
)
@Stable
data class Celebrity(
    val name: String,
    val img: String,
)
