package com.unifest.android.core.network.response.stamp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectedStampsResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<StampRecord>,
)

@Serializable
data class StampRecord(
    @SerialName("stampRecordId")
    val stampRecordId: Long,
    @SerialName("boothId")
    val boothId: Long,
    @SerialName("festivalId")
    val festivalId: Long,
    @SerialName("deviceId")
    val deviceId: String,
)
