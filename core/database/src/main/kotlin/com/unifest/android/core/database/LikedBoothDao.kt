package com.unifest.android.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unifest.android.core.database.entity.LikedBoothEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedBoothDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedBooth(userInfo: LikedBoothEntity)

    @Delete
    suspend fun deleteLikedBooth(userInfo: LikedBoothEntity)

    @Query("SELECT * FROM liked_booth")
    fun getLikedBoothList(): Flow<List<LikedBoothEntity>>
}