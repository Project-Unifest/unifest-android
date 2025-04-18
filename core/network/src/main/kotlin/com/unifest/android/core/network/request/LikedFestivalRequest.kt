package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikedFestivalRequest(
    @SerialName("deviceId")
    val deviceId: String,
)
