package com.unifest.android.feature.map.mapper

import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.MenuModel
import com.unifest.android.feature.map.model.BoothDetailMapModel
import com.unifest.android.feature.map.model.MenuMapModel
import kotlinx.collections.immutable.toImmutableList

internal fun BoothDetailModel.toMapModel() =
    BoothDetailMapModel(
        id = id,
        name = name,
        category = category,
        description = description,
        warning = warning,
        location = location,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble(),
        menus = menus.map { it.toMapModel() }.toImmutableList(),
    )

internal fun MenuModel.toMapModel() =
    MenuMapModel(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )

internal fun BoothDetailMapModel.toModel() =
    BoothDetailModel(
        id = id,
        name = name,
        category = category,
        description = description,
        warning = warning,
        location = location,
        latitude = latitude.toFloat(),
        longitude = longitude.toFloat(),
        menus = menus.map { it.toModel() }.toImmutableList(),
    )

internal fun MenuMapModel.toModel() =
    MenuModel(
        id = id,
        name = name,
        price = price,
        imgUrl = imgUrl,
    )
