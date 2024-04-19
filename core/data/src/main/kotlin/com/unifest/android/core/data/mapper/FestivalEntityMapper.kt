package com.unifest.android.core.data.mapper

import com.unifest.android.core.database.entity.LikedFestivalEntity
import com.unifest.android.core.database.entity.StarListEntity
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.model.StarListModel

internal fun LikedFestivalEntity.toModel(): FestivalModel {
    return FestivalModel(
        festivalId = festivalId,
        schoolId = schoolId,
        thumbnail = thumbnail ?: "",
        schoolName = schoolName,
        festivalName = festivalName,
        beginDate = beginDate ?: "",
        endDate = endDate ?: "",
        latitude = latitude ?: 0f,
        longitude = longitude ?: 0f,
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
    )
}

internal fun StarListModel.toEntity(): StarListEntity {
    return StarListEntity(
        name = name,
        img = img,
    )
}

internal fun FestivalModel.toEntity(): LikedFestivalEntity {
    return LikedFestivalEntity(
        festivalId = festivalId,
        schoolName = schoolName,
        festivalName = festivalName,
        schoolId = schoolId,
        thumbnail = thumbnail,
        beginDate = beginDate,
        endDate = endDate,
        latitude = latitude,
        longitude = longitude,
    )
}
