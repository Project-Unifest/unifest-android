package com.unifest.android.core.network.response.booth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeBoothResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Int,
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
