package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.StarListModel
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.network.response.StarList
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
        date = date,
        starList = starList.map { it.toModel() },
        schoolId = schoolId,
        thumbnail = thumbnail,
    )
}

internal fun StarList.toModel(): StarListModel {
    return StarListModel(
        name = name,
        img = img,
    )
}
