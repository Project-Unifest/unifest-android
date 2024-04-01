package com.unifest.android.core.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostLikesRequest(
    @SerialName("boothId")
    val boothId: Float,
    @SerialName("token")
    val token: String,
)
