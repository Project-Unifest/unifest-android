package com.unifest.android.core.network.response.booth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoothDetailResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: BoothDetail,
)

@Serializable
data class BoothDetail(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("thumbnail")
    val thumbnail: String? = null,
    @SerialName("warning")
    val warning: String? = null,
    @SerialName("location")
    val location: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("menus")
    val menus: List<Menu>,
    @SerialName("waitingEnabled")
    val waitingEnabled: Boolean,
    @SerialName("scheduleList")
    val scheduleList: List<Schedule>,
    @SerialName("stampEnabled")
    val stampEnabled: Boolean,
)

@Serializable
data class Schedule(
    @SerialName("id")
    val id: Long,
    @SerialName("date")
    val date: String,
    @SerialName("openTime")
    val openTime: String,
    @SerialName("closeTime")
    val closeTime: String,
)
