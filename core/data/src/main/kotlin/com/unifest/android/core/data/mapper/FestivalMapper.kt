package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.StarInfoModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.network.response.StarInfo
import com.unifest.android.core.network.response.FestivalSearch
import com.unifest.android.core.network.response.FestivalToday

internal fun FestivalSearch.toModel(): FestivalModel {
    return FestivalModel(
        festivalId = festivalId,
        schoolId = schoolId,
        thumbnail = thumbnail,
        schoolName = schoolName,
        festivalName = festivalName,
        beginDate = beginDate,
        endDate = endDate,
        latitude = latitude,
        longitude = longitude,
    )
}

internal fun FestivalToday.toModel(): FestivalTodayModel {
    return FestivalTodayModel(
        schoolName = schoolName,
        festivalName = festivalName,
        festivalId = festivalId,
        beginDate = beginDate,
        endDate = endDate,
        starInfo = starInfo.map { it.toModel() },
        schoolId = schoolId,
        thumbnail = thumbnail,
    )
}

internal fun StarInfo.toModel(): StarInfoModel {
    return StarInfoModel(
        name = name,
        img = img,
    )
}
