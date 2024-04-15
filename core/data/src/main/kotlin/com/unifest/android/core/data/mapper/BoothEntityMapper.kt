package com.unifest.android.core.data.mapper

import com.unifest.android.core.database.entity.LikedBoothEntity
import com.unifest.android.core.database.entity.MenuEntity
import com.unifest.android.core.model.BoothDetail
import com.unifest.android.core.model.Menu

internal fun LikedBoothEntity.toModel(): BoothDetail {
    return BoothDetail(
        id = id,
        name = name,
        category = category,
        description = description,
        warning = warning,
        location = location,
        latitude = latitude,
        longitude = longitude,
        menus = menus.map { it.toModel() },
    )
}

internal fun MenuEntity.toModel(): Menu {
    return Menu(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
}

internal fun BoothDetail.toEntity(): LikedBoothEntity {
    return LikedBoothEntity(
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

internal fun Menu.toEntity(): MenuEntity {
    return MenuEntity(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
}
