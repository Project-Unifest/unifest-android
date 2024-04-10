package com.unifest.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class BoothSpot(
    val id: Long,
    val lat: Double,
    val lng: Double,
)
