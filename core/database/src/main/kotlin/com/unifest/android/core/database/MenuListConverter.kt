package com.unifest.android.core.database

import androidx.room.TypeConverter
import com.unifest.android.core.database.entity.MenuEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MenuListConverter {
    private val json = Json

    @TypeConverter
    fun fromMenuList(menuList: List<MenuEntity>): String {
        return json.encodeToString(menuList)
    }

    @TypeConverter
    fun toMenuList(menuListString: String): List<MenuEntity> {
        return json.decodeFromString(menuListString)
    }
}
