package com.unifest.android.core.network.response.stamp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StampFestivalsResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<StampFestival>,
)

@Serializable
data class StampFestival(
    @SerialName("festivalId")
    val festivalId: Long,
    @SerialName("name")
    val name: String,
)

