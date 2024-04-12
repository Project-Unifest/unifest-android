package com.unifest.android.core.database

import androidx.room.TypeConverter
import com.unifest.android.core.database.entity.MenuEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OrmConverter {
    private val json = Json

    @TypeConverter
    fun fromMenu(menu: MenuEntity?): String? {
        return menu?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toMenu(menuString: String?): MenuEntity? {
        return menuString?.let { json.decodeFromString(it) }
    }
}
