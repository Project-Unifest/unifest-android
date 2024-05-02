package com.unifest.android.feature.map.mapper

import com.unifest.android.core.model.AllBoothsModel
import com.unifest.android.feature.map.model.AllBoothsMapModel

internal fun AllBoothsModel.toMapModel() =
    AllBoothsMapModel(
        id = id,
        name = name,
        category = category,
        description = description,
        location = location,
        latitude = latitude.toDouble(),
        longitude = longitude.toDouble(),
    )

internal fun AllBoothsMapModel.toModel() =
    AllBoothsModel(
        id = id,
        name = name,
        category = category,
        description = description,
        location = location,
        latitude = latitude.toFloat(),
        longitude = longitude.toFloat(),
    )
