package com.unifest.android.core.domain.mapper

import com.unifest.android.core.data.response.BoothDetailResponse
import com.unifest.android.core.data.response.MenuResponse
import com.unifest.android.core.domain.entity.BoothDetailEntity
import com.unifest.android.core.domain.entity.MenuEntity

fun BoothDetailResponse.toEntity(): BoothDetailEntity {
    return BoothDetailEntity(
        id = id,
        name = name,
        category = category,
        description = description,
        warning = warning,
        location = location,
        latitude = latitude,
        longitude = longitude,
        menus = menus.map { it.toEntity() },
        isLiked = false,
    )
}

fun MenuResponse.toEntity(): MenuEntity {
    return MenuEntity(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
}
