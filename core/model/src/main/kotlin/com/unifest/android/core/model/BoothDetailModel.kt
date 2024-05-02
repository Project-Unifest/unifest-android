package com.unifest.android.core.model

import androidx.compose.runtime.Stable

@Stable
data class BoothDetailModel(
    val id: Long = 0L,
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val warning: String = "",
    val location: String = "",
    val latitude: Float = 0F,
    val longitude: Float = 0F,
    val menus: List<MenuModel> = emptyList(),
    val likes: Int = 0,
    val isLiked: Boolean = false,
)

@Stable
data class MenuModel(
    val id: Long,
    val name: String,
    val price: Int,
    val imgUrl: String,
)
