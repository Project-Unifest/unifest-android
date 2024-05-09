package com.unifest.android.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unifest.android.core.database.entity.LikedBoothEntity

@Database(
    entities = [LikedBoothEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(MenuListConverter::class)
abstract class LikedBoothDatabase : RoomDatabase() {
    abstract fun likedBoothDao(): LikedBoothDao
}
