package com.unifest.android.core.network.response

import com.unifest.android.core.model.BoothDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularBoothsResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<BoothDetail>,
)
