package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class Festival(
    val imgUrl: String,
    val schoolName: String,
    val festivalName: String,
    val festivalDate: String,
)
