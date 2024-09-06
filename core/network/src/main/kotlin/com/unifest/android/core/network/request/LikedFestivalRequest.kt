package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikedFestivalRequest(
    @SerialName("festivalId")
    val festivalId: Long,
    @SerialName("fcmToken")
    val fcmToken: String,
)
