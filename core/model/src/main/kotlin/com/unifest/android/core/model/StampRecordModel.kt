package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class StampRecordModel(
    val stampRecordId: Long,
    val boothId: Long,
    val festivalId: Long,
    val deviceId: String,
)
