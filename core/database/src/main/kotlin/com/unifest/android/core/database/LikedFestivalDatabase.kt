package com.unifest.android.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unifest.android.core.database.entity.LikedFestivalEntity

@Database(
    entities = [LikedFestivalEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(StarListConverter::class)
abstract class LikedFestivalDatabase : RoomDatabase() {
    abstract fun likedFestivalDao(): LikedFestivalDao
}
