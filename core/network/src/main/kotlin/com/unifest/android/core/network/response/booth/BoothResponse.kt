package com.unifest.android.core.network.response.booth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
data class Menu(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("imgUrl")
    val imgUrl: String? = null,
    @SerialName("menuStatus")
    val status: String? = null,
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
