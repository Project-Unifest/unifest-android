package com.unifest.android.core.data.mapper

import com.unifest.android.core.data.response.BoothDetailResponse
import com.unifest.android.core.data.response.MenuResponse
import com.unifest.android.core.database.entity.LikedBoothEntity
import com.unifest.android.core.database.entity.MenuEntity

internal fun LikedBoothEntity.toResponse(): BoothDetailResponse {
    return BoothDetailResponse(
        id = id,
        name = name,
        category = category,
        description = description,
        warning = warning,
        location = location,
        latitude = latitude,
        longitude = longitude,
        menus = menus.map { it.toResponse() }
    )
}

internal fun MenuEntity.toResponse(): MenuResponse {
    return MenuResponse(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl
    )
}
