package com.unifest.android.core.domain.entity

data class BoothCreateEntity(
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
