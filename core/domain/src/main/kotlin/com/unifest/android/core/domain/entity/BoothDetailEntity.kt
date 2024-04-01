package com.unifest.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class BoothDetailEntity(
    val id: Long,
    val name: String,
    val category: String,
    val description: String,
    val warning: String,
    val location: String,
    val latitude: Float,
    val longitude: Float,
    val menus: List<MenuEntity>,
)

@Stable
data class MenuEntity(
    val id: Long,
    val name: String,
    val price: Int,
    val imgUrl: String,
)
