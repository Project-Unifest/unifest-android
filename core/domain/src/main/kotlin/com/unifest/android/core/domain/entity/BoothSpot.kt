package com.unifest.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class BoothSpot(
    val lat: Double,
    val lng: Double,
    val id: Int? = null
)
