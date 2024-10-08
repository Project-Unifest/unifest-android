package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class StampBoothModel(
    val id: Long,
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val location: String = "",
    val latitude: Float = 0F,
    val longitude: Float = 0F,
    val isChecked: Boolean = false,
)
