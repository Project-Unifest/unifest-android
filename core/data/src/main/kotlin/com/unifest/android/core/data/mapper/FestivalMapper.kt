package com.unifest.android.core.data.mapper

import com.unifest.android.core.database.entity.LikedFestivalEntity
import com.unifest.android.core.database.entity.StarListEntity
import com.unifest.android.core.model.StarListModel
import com.unifest.android.core.model.FestivalSearchModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.network.response.StarList
import com.unifest.android.core.network.response.FestivalSearch
import com.unifest.android.core.network.response.FestivalToday


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
        schoolId = schoolId,
    )
}

internal fun StarList.toModel(): StarListModel {
    return StarListModel(
        name = name,
        img = img,
    )
}

internal fun FestivalTodayModel.toEntity(): LikedFestivalEntity {
    return LikedFestivalEntity(
        festivalId = festivalId,
        schoolName = schoolName,
        festivalName = festivalName,
        date = date,
        starList = starList.map { it.toEntity() },
        schoolId = schoolId,
        beginDate = "",
        endDate = "",
        latitude = 0f,
        longitude = 0f,
        thumbnail = "",
    )
}

internal fun StarListModel.toEntity(): StarListEntity {
    return StarListEntity(
        name = name,
        img = img,
    )
}


