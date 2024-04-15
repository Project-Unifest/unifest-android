package com.unifest.android.core.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class FestivalSearch(
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
