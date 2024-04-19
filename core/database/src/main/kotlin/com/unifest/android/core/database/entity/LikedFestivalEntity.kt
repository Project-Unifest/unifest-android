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
    val date: String? = null,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String? = null,

    @ColumnInfo(name = "begin_date")
    val beginDate: String? = null,

    @ColumnInfo(name = "end_date")
    val endDate: String? = null,

    @ColumnInfo(name = "star_list")
    val starList: List<StarListEntity>? = null,

    @ColumnInfo(name = "latitude")
    val latitude: Float? = null,

    @ColumnInfo(name = "longitude")
    val longitude: Float? = null,

)

@Serializable
data class StarListEntity(
    val name: String = "",
    val img: String = "",
)
