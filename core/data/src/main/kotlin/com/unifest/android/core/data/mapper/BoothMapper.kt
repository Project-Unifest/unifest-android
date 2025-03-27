package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.BoothModel
import com.unifest.android.core.model.LikedBoothModel
import com.unifest.android.core.model.MenuModel
import com.unifest.android.core.model.ScheduleModel
import com.unifest.android.core.model.StampBoothModel
import com.unifest.android.core.model.WaitingModel
import com.unifest.android.core.network.response.waiting.Waiting
import com.unifest.android.core.network.response.booth.Booth
import com.unifest.android.core.network.response.booth.BoothDetail
import com.unifest.android.core.network.response.booth.LikedBooth
import com.unifest.android.core.network.response.booth.Menu
import com.unifest.android.core.network.response.booth.Schedule
import com.unifest.android.core.network.response.booth.StampBooth

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
        scheduleList = scheduleList.map { it.toModel() },
        stampEnabled = stampEnabled,
    )
}

internal fun Schedule.toModel(): ScheduleModel {
    return ScheduleModel(
        id = id,
        date = date,
        openTime = openTime,
        closeTime = closeTime,
    )
}

internal fun Menu.toModel(): MenuModel {
    return MenuModel(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl ?: "",
        status = status ?: "",
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

internal fun StampBooth.toModel(): StampBoothModel {
    return StampBoothModel(
        id = id,
        name = name,
        category = category,
        description = description,
        thumbnail = thumbnail,
        location = location,
        latitude = latitude,
        longitude = longitude,
        enabled = enabled,
        waitingEnabled = waitingEnabled,
        openTime = openTime,
        closeTime = closeTime,
        stampEnabled = stampEnabled,
    )
}
