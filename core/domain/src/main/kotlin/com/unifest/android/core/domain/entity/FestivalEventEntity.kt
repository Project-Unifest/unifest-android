package com.unifest.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class FestivalEventEntity(
    val id: Int,
    val date: String,
    val name: String,
    val location: String,
    val celebrityImages: List<Int>,
)
