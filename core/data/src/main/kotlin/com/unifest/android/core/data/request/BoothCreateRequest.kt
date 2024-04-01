package com.unifest.android.core.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoothCreateRequest(
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("detail")
    val detail: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("festivalId")
    val festivalId: Long,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("enabled")
    val enabled: Boolean,
)
