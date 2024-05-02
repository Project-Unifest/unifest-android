package com.unifest.android.core.network.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeBoothRequest(
    @SerialName("boothId")
    val boothId: Long,
    @SerialName("token")
    val token: String,
)
