package com.unifest.android.core.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuCreateRequest(
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int,
    @SerialName("imgUrl")
    val imgUrl: String,
)
