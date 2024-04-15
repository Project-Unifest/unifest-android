package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class FestivalEvent(
    val id: Int,
    val date: String,
    val name: String,
    val location: String,
    val celebrityImages: List<Int>,
)
