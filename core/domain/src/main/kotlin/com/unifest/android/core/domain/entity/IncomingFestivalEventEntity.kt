package com.unifest.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class IncomingFestivalEventEntity(
    val imageRes: Int,
    val name: String,
    val dates: String,
    val location: String
)
