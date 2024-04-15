package com.unifest.android.core.network.response

import com.unifest.android.core.model.FestivalSearch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FestivalSearchResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<FestivalSearch>,
)
