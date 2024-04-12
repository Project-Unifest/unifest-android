package com.unifest.android.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unifest.android.core.database.entity.InterestedBoothEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestedBoothDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterestedBooth(userInfo: InterestedBoothEntity)

    @Delete
    suspend fun deleteInterestedBooth(userInfo: InterestedBoothEntity)

    @Query("SELECT * FROM interested_booth")
    fun getInterestedBooths(): Flow<List<InterestedBoothEntity>>
}
