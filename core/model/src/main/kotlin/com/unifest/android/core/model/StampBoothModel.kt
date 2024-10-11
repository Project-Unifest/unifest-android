package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class StampBoothModel(
    val id: Long,
    val name: String,
    val category: String,
    val description: String? = null,
    val thumbnail: String? = null,
    val location: String,
    val latitude: Float,
    val longitude: Float,
    val enabled: Boolean,
    val waitingEnabled: Boolean,
    val openTime: String? = null,
    val closeTime: String? = null,
    val stampEnabled: Boolean,
)
