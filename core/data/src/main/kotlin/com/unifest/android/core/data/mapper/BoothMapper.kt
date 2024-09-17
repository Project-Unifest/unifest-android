package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.BoothModel
import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.core.model.MenuModel
import com.unifest.android.core.model.WaitingModel
import com.unifest.android.core.network.response.Booth
import com.unifest.android.core.network.response.BoothDetail
import com.unifest.android.core.network.response.LikedBooth
import com.unifest.android.core.network.response.Menu
import com.unifest.android.core.network.response.Waiting

internal fun BoothDetail.toModel(): BoothDetailModel {
    return BoothDetailModel(
        id = id,
        name = name,
        category = category,
        description = description ?: "",
        thumbnail = thumbnail ?: "",
        warning = warning ?: "",
        location = location,
        latitude = latitude,
        longitude = longitude,
        menus = menus.map { it.toModel() },
        waitingEnabled = waitingEnabled,
        openTime = openTime ?: "등록된 정보가 없습니다",
        closeTime = closeTime ?: "등록된 정보가 없습니다",
    )
}

internal fun Menu.toModel(): MenuModel {
    return MenuModel(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl ?: "",
        status = status ?: "등록된 정보가 없습니다",
    )
}

internal fun Booth.toModel(): BoothModel {
    return BoothModel(
        id = id,
        name = name,
        category = category,
        description = description,
        thumbnail = thumbnail,
        location = location,
        latitude = latitude,
        longitude = longitude,
    )
}

internal fun LikedBooth.toModel(): LikedBoothModel {
    return LikedBoothModel(
        id = id,
        name = name,
        category = category,
        description = description,
        thumbnail = thumbnail,
        location = location,
        latitude = latitude,
        longitude = longitude,
        warning = warning,
    )
}

internal fun Waiting.toModel(): WaitingModel {
    return WaitingModel(
        boothId = boothId,
        waitingId = waitingId,
        partySize = partySize,
        tel = tel,
        deviceId = deviceId,
        createdAt = createdAt,
        updatedAt = updatedAt,
        status = status,
        waitingOrder = waitingOrder ?: 0L,
        boothName = boothName,
    )
}
