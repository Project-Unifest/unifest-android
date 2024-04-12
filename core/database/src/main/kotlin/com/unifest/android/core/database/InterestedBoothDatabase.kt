package com.unifest.android.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.unifest.android.core.database.entity.InterestedBoothEntity

@Database(
    entities = [InterestedBoothEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(OrmConverter::class)
abstract class InterestedBoothDatabase : RoomDatabase() {
    abstract fun interestedBoothDao(): InterestedBoothDao
}
