package com.unifest.android.core.data.repository

import com.unifest.android.core.model.BoothDetailModel
import kotlinx.coroutines.flow.Flow

interface LikedBoothRepository {
    fun getLikedBoothList(): Flow<List<BoothDetailModel>>
    suspend fun insertLikedBooth(booth: BoothDetailModel)
    suspend fun deleteLikedBooth(booth: BoothDetailModel)
    suspend fun updateLikedBooth(booth: BoothDetailModel)
}
