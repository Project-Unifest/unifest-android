package com.unifest.android.feature.map.mapper

import com.unifest.android.core.model.BoothDetail
import com.unifest.android.core.model.Menu
import com.unifest.android.feature.map.model.BoothDetailModel
import com.unifest.android.feature.map.model.MenuModel
import kotlinx.collections.immutable.toImmutableList

internal fun BoothDetail.toModel() =
    BoothDetailModel(
        id = id,
        name = name,
        category = category,
        description = description,
        warning = warning,
        location = location,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble(),
        menus = menus.map { it.toModel() }.toImmutableList(),
    )

internal fun Menu.toModel() =
    MenuModel(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
