package com.unifest.android.core.data.mapper

import com.unifest.android.core.model.CelebrityModel
import com.unifest.android.core.model.FestivalSearchModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.MenuModel
import com.unifest.android.core.network.response.Celebrity
import com.unifest.android.core.network.response.FestivalSearch
import com.unifest.android.core.network.response.FestivalToday
import com.unifest.android.core.network.response.Menu
import kotlinx.serialization.SerialName

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

internal fun FestivalToday.toModel(): FestivalTodayModel {
    return FestivalTodayModel(
        schoolName = schoolName,
        festivalName = festivalName,
        festivalId = festivalId,
        date = date,
        starList = starList.map { it.toModel() },

    )
}

internal fun Celebrity.toModel(): CelebrityModel {
    return CelebrityModel(
        name = name,
        img = img,
    )
}
