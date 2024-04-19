package com.unifest.android.core.database

import androidx.room.TypeConverter
import com.unifest.android.core.database.entity.StarInfoEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StarInfoConverter {
    private val json = Json

    @TypeConverter
    fun fromStarInfo(starInfo: List<StarInfoEntity>): String {
        return json.encodeToString(starInfo)
    }

    @TypeConverter
    fun toStarInfo(starInfoString: String): List<StarInfoEntity> {
        return json.decodeFromString(starInfoString)
    }
}
