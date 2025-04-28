package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.StampBoothModel
import com.unifest.android.core.model.StampFestivalModel
import com.unifest.android.core.network.response.stamp.StampBooth
import com.unifest.android.core.model.StampRecordModel
import com.unifest.android.core.network.response.stamp.StampFestival
import com.unifest.android.core.network.response.stamp.StampRecord

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

internal fun StampRecord.toModel(): StampRecordModel {
    return StampRecordModel(
        stampRecordId = stampRecordId,
        boothId = boothId,
        festivalId = festivalId,
        deviceId = deviceId,
    )
}

internal fun StampFestival.toModel(): StampFestivalModel {
    return StampFestivalModel(
        festivalId = festivalId,
        schoolName = schoolName,
        defaultImgUrl = defaultImgUrl,
        usedImgUrl = usedImgUrl,
    )
}
