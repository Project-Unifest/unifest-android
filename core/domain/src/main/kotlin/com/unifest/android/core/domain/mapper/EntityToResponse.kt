package com.unifest.android.core.domain.mapper

import com.unifest.android.core.data.response.BoothDetailResponse
import com.unifest.android.core.data.response.MenuResponse
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity

fun BoothDetailEntity.toResponse(): BoothDetailResponse {
    return BoothDetailResponse(
        id = id,
        name = name,
        category = category,
        description = description,
        warning = warning,
        location = location,
        latitude = latitude,
        longitude = longitude,
        menus = menus.map { it.toResponse() },
    )
}

fun MenuEntity.toResponse(): MenuResponse {
    return MenuResponse(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
}
