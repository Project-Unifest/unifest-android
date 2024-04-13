package com.unifest.android.core.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class BoothDetailEntity(
    val id: Long = 0L,
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val warning: String = "",
    val location: String = "",
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val menus: List<MenuEntity> = emptyList(),
    val isLiked: Boolean = false,
)

@Stable
data class MenuEntity(
    val id: Long = 0L,
    val name: String = "",
    val price: Int = 0,
    val imgUrl: String = "",
)
