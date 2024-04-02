package com.unifest.android.core.domain.entity

data class FestivalEventEntity(
    val id: Int,
    val date: String,
    val name: String,
    val location: String,
    val celebrityImages: List<Int>,
)
