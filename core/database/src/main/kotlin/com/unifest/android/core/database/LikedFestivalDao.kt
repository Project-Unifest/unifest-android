package com.unifest.android.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unifest.android.core.database.entity.LikedFestivalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedFestivalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedFestival(festivals: LikedFestivalEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedFestivals(festivals: List<LikedFestivalEntity>)

    @Delete
    suspend fun deleteLikedFestival(festival: LikedFestivalEntity)

    @Query("SELECT * FROM liked_festival")
    fun getLikedFestivalList(): Flow<List<LikedFestivalEntity>>
}
