package com.unifest.android.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "liked_booth")
data class LikedBoothEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "warning")
    val warning: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "latitude")
    val latitude: Float,
    @ColumnInfo(name = "longitude")
    val longitude: Float,
    @ColumnInfo(name = "menus")
    val menus: List<MenuEntity>,
)

@Serializable
data class MenuEntity(
    val id: Long = 0L,
    val name: String = "",
    val price: Int = 0,
    val imgUrl: String = "",
)
