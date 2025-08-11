package com.unifest.android.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeInfoResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: HomeInfo,
)

@Serializable
data class HomeInfo(
    @SerialName("homeCardList")
    val homeCardList: List<HomeCard>,
    @SerialName("homeTipList")
    val homeTipList: List<HomeTip>,
)

@Serializable
data class HomeCard(
    @SerialName("createdDate")
    val createdDate: String,
    @SerialName("detailImgUrl")
    val detailImgUrl: String,
    @SerialName("id")
    val id: Long,
    @SerialName("modifiedDate")
    val modifiedDate: String,
    @SerialName("thumbnailImgUrl")
    val thumbnailImgUrl: String,
)

@Serializable
data class HomeTip(
    @SerialName("createdDate")
    val createdDate: String,
    @SerialName("id")
    val id: Long,
    @SerialName("modifiedDate")
    val modifiedDate: String,
    @SerialName("tipContent")
    val tipContent: String,
)
