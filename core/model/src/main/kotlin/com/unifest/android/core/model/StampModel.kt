package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class StampModel(
    val boothId: Long,
    val isChecked: Boolean = false,
)
