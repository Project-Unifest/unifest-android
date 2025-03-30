package com.unifest.android.core.data.api.repository

import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.LikedBoothModel
import kotlinx.coroutines.flow.Flow

interface LikedBoothRepository {
    suspend fun getLikedBooths(): Result<List<LikedBoothModel>>
    fun getLikedBoothList(): Flow<List<BoothDetailModel>>
    suspend fun insertLikedBooth(booth: BoothDetailModel)
    suspend fun deleteLikedBooth(booth: BoothDetailModel)
    suspend fun updateLikedBooth(booth: BoothDetailModel)
    suspend fun isLikedBooth(booth: BoothDetailModel): Boolean
}
