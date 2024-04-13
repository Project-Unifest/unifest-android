package com.unifest.android.core.data.repository

import com.unifest.android.core.data.response.BoothDetailResponse
import kotlinx.coroutines.flow.Flow

interface LikedBoothRepository {
    fun getLikedBoothList(): Flow<List<BoothDetailResponse>>
    suspend fun insertLikedBooth(booth: BoothDetailResponse)
    suspend fun deleteLikedBooth(booth: BoothDetailResponse)
    suspend fun updateLikedBooth(booth: BoothDetailResponse)
}
