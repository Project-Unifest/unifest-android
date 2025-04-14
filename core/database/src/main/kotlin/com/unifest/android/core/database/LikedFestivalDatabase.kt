package com.unifest.android.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.unifest.android.core.database.entity.LikedFestivalEntity

@Database(
    entities = [LikedFestivalEntity::class],
    version = 2,
    exportSchema = true,
)
@TypeConverters(StarInfoConverter::class)
abstract class LikedFestivalDatabase : RoomDatabase() {
    abstract fun likedFestivalDao(): LikedFestivalDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // created_at 컬럼을 추가하고 기본값으로 현재 시간 설정
        database.execSQL("ALTER TABLE liked_festival ADD COLUMN created_at INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()}")
    }
}
