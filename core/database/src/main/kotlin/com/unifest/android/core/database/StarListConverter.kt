package com.unifest.android.core.database

import androidx.room.TypeConverter
import com.unifest.android.core.database.entity.MenuEntity
import com.unifest.android.core.database.entity.StarListEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StarListConverter {
    private val json = Json

    @TypeConverter
    fun fromStarList(starList: List<StarListEntity>): String {
        return json.encodeToString(starList)
    }

    @TypeConverter
    fun toStarList(starListString: String): List<StarListEntity> {
        return json.decodeFromString(starListString)
    }
}
