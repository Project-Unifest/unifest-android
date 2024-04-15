package com.unifest.android.core.data.repository

import com.unifest.android.core.model.BoothDetail
import kotlinx.coroutines.flow.Flow

interface LikedBoothRepository {
    fun getLikedBoothList(): Flow<List<BoothDetail>>
    suspend fun insertLikedBooth(booth: BoothDetail)
    suspend fun deleteLikedBooth(booth: BoothDetail)
    suspend fun updateLikedBooth(booth: BoothDetail)
}
