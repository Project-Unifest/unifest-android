package com.unifest.android.core.model

data class BoothCreateModel(
    val name: String,
    val category: String,
    val description: String,
    val detail: String,
    val thumbnail: String,
    val festivalId: Long,
    val latitude: Float,
    val longitude: Float,
    val enabled: Boolean,
)
