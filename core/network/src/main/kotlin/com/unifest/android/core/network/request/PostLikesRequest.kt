package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostLikesRequest(
    @SerialName("boothId")
    val boothId: Float,
    @SerialName("token")
    val token: String,
)
