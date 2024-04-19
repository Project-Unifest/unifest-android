package com.unifest.android.core.network.response

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

@Serializable
data class FestivalSearch(
    @SerialName("schoolId")
    val schoolId: Int,
    @SerialName("festivalId")
    val festivalId: Int,
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

@Serializable
data class FestivalTodayResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<FestivalToday>,
)

@Serializable
data class FestivalToday(
    @SerialName("schoolId")
    val schoolId: Int,
    @SerialName("festivalId")
    val festivalId: Int,
    @SerialName("date")
    val date: String,
    @SerialName("festivalName")
    val festivalName: String,
    @SerialName("schoolName")
    val schoolName: String,
    @SerialName("starList")
    val starList: List<StarList>,
    @SerialName("thumbnail")
    val thumbnail: String,
)

@Serializable
data class StarList(
    @SerialName("name")
    val name: String,
    @SerialName("img")
    val img: String,
)
