package com.unifest.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class School(
    val image: String,
    val schoolName: String,
    val festivalName: String,
    val festivalDate: String,
)
