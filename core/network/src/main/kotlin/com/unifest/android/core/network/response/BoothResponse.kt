package com.unifest.android.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoothDetailResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: BoothDetail,
)

@Serializable
data class PopularBoothsResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<Booth>,
)

@Serializable
data class AllBoothsResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<Booth>,
)

@Serializable
data class LikedBoothsResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<LikedBooth>,
)

@Serializable
data class BoothDetail(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("warning")
    val warning: String? = null,
    @SerialName("location")
    val location: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("menus")
    val menus: List<Menu>,
)

@Serializable
data class Menu(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("imgUrl")
    val imgUrl: String? = null,
)

@Serializable
data class Booth(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("location")
    val location: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
)

@Serializable
data class LikedBooth(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("location")
    val location: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("warning")
    val warning: String,
)
