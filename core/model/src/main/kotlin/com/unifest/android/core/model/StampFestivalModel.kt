package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class StampFestivalModel(
    val festivalId: Long,
    val schoolName: String,
    val defaultImgUrl: String,
    val usedImgUrl: String,
)
