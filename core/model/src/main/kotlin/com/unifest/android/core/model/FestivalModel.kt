package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class FestivalModel(
    val festivalId: Long = 0L,
    val schoolId: Long = 0L,
    val thumbnail: String = "",
    val schoolName: String = "",
    val region: String = "",
    val festivalName: String = "",
    val beginDate: String = "",
    val endDate: String = "",
    val latitude: Float = 0.0F,
    val longitude: Float = 0.0F,
)
