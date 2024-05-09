package com.unifest.android.feature.map.mapper

import com.unifest.android.core.model.BoothModel
import com.unifest.android.feature.map.model.BoothMapModel

internal fun BoothModel.toMapModel() =
    BoothMapModel(
        id = id,
        name = name,
        category = category,
        description = description,
        thumbnail = thumbnail,
        location = location,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble(),
    )

internal fun BoothMapModel.toModel() =
    BoothModel(
        id = id,
        name = name,
        category = category,
        description = description,
        thumbnail = thumbnail,
        location = location,
        latitude = latitude.toFloat(),
        longitude = longitude.toFloat(),
    )
