package com.unifest.android.core.network.response.booth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StampBoothsResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<StampBooth>,
)

@Serializable
data class StampBooth(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("thumbnail")
    val thumbnail: String? = null,
    @SerialName("location")
    val location: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("enabled")
    val enabled: Boolean,
    @SerialName("waitingEnabled")
    val waitingEnabled: Boolean,
    @SerialName("openTime")
    val openTime: String? = null,
    @SerialName("closeTime")
    val closeTime: String? = null,
    @SerialName("stampEnabled")
    val stampEnabled: Boolean,
)
