package com.unifest.android.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "liked_festival")
data class LikedFestivalEntity(
    @PrimaryKey
    @ColumnInfo(name = "festival_id")
    val festivalId: Int,

    @ColumnInfo(name = "school_id")
    val schoolId: Int,

    @ColumnInfo(name = "school_name")
    val schoolName: String,

    @ColumnInfo(name = "festival_name")
    val festivalName: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,

    @ColumnInfo(name = "begin_date")
    val beginDate: String,

    @ColumnInfo(name = "end_date")
    val endDate: String,

    @ColumnInfo(name = "star_list")
    val starList: List<StarListEntity>,

    @ColumnInfo(name = "latitude")
    val latitude: Float,

    @ColumnInfo(name = "longitude")
    val longitude: Float,



)

@Serializable
data class StarListEntity(
    val name: String = "",
    val img: String = "",
)

