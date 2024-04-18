package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class FestivalModel(
    val festivalId: Int,
    val schoolId: Int,
    val thumbnail: String,
    val schoolName: String,
    val festivalName: String,
    val beginDate: String,
    val endDate: String,
    val latitude: Float,
    val longitude: Float,
)
