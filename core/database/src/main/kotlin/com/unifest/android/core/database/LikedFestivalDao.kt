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
    suspend fun insertLikedFestival(userInfo: LikedFestivalEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM liked_festival WHERE festival_id = :festivalId)")
    suspend fun isFestivalExists(festivalId: Long): Boolean

    @Delete
    suspend fun deleteLikedFestival(userInfo: LikedFestivalEntity)

    @Query("SELECT * FROM liked_festival")
    fun getLikedFestivalList(): Flow<List<LikedFestivalEntity>>
}
