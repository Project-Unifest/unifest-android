package com.unifest.android.core.data.mapper

import com.unifest.android.core.data.response.BoothDetailResponse
import com.unifest.android.core.data.response.MenuResponse
import com.unifest.android.core.database.entity.LikedBoothEntity
import com.unifest.android.core.database.entity.MenuEntity

internal fun BoothDetailResponse.toDBEntity(): LikedBoothEntity {
    return LikedBoothEntity(
        id = id,
        name = name,
        category = category,
        description = description,
        warning = warning,
        location = location,
        latitude = latitude,
        longitude = longitude,
        menus = menus.map { it.toDBEntity() },
        isLiked = false,
    )
}

internal fun MenuResponse.toDBEntity(): MenuEntity {
    return MenuEntity(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
}
