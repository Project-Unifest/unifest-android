package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class IncomingFestivalEvent(
    val imageRes: Int,
    val name: String,
    val dates: String,
    val location: String,
)
