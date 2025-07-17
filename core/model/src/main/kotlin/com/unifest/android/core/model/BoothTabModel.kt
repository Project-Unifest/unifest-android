package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class BoothTabModel(
    val id: Long = 0L,
    val name: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val location: String = "",
    val waitingEnabled: Boolean = false,
)
