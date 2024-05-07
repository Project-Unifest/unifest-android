package com.unifest.android.core.data.mapper

import com.unifest.android.core.database.entity.LikedBoothEntity
import com.unifest.android.core.database.entity.MenuEntity
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.MenuModel

internal fun LikedBoothEntity.toModel(): BoothDetailModel {
    return BoothDetailModel(
        id = id,
        name = name,
        category = category,
        description = description,
        thumbnail = thumbnail,
        warning = warning,
        location = location,
        latitude = latitude,
        longitude = longitude,
        menus = menus.map { it.toModel() },
    )
}

internal fun MenuEntity.toModel(): MenuModel {
    return MenuModel(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
}

internal fun BoothDetailModel.toEntity(): LikedBoothEntity {
    return LikedBoothEntity(
        id = id,
        name = name,
        category = category,
        description = description,
        thumbnail = thumbnail,
        warning = warning,
        location = location,
        latitude = latitude,
        longitude = longitude,
        menus = menus.map { it.toEntity() },
        isLiked = true,
    )
}

internal fun MenuModel.toEntity(): MenuEntity {
    return MenuEntity(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
}
