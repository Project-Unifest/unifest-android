package com.unifest.android.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoothDetailResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("warning")
    val warning: String,
    @SerialName("location")
    val location: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("menus")
    val menus: List<MenuResponse>,
)

@Serializable
data class MenuResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("imgUrl")
    val imgUrl: String,
)
