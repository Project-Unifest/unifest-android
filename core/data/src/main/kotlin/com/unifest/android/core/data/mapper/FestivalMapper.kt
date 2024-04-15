package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.FestivalSearchModel
import com.unifest.android.core.network.response.FestivalSearch

internal fun FestivalSearch.toModel(): FestivalSearchModel {
    return FestivalSearchModel(
        thumbnail = thumbnail,
        schoolName = schoolName,
        festivalName = festivalName,
        beginDate = beginDate,
        endDate = endDate,
        latitude = latitude,
        longitude = longitude,
    )
}
