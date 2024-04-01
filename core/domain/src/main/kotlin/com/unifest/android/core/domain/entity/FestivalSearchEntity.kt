package com.unifest.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class FestivalSearchEntity(
    val thumbnail: String,
    val schoolName: String,
    val festivalName: String,
    val beginDate: String,
    val endDate: String,
    val latitude: Float,
    val longitude: Float,
)
