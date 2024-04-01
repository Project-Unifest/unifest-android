package com.unifest.android.core.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FestivalSearchResponse(
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("schoolName")
    val schoolName: String,
    @SerialName("festivalName")
    val festivalName: String,
    @SerialName("beginDate")
    val beginDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
)
