package com.unifest.android.core.data.repository

import com.unifest.android.core.data.mapper.toDBEntity
import com.unifest.android.core.data.mapper.toResponse
import com.unifest.android.core.data.response.BoothDetailResponse
import com.unifest.android.core.database.LikedBoothDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultLikedBoothRepository @Inject constructor(
    private val likedBoothDao: LikedBoothDao,
) : LikedBoothRepository {
    override fun getLikedBoothList(): Flow<List<BoothDetailResponse>> {
        return likedBoothDao.getLikedBoothList().map { likedBooths ->
            likedBooths.map { likedBooth ->
                likedBooth.toResponse()
            }
        }
    }

    override suspend fun insertLikedBooth(booth: BoothDetailResponse) {
        likedBoothDao.insertLikedBooth(booth.toDBEntity())
    }

    override suspend fun deleteLikedBooth(booth: BoothDetailResponse) {
        likedBoothDao.deleteLikedBooth(booth.toDBEntity())
    }

    override suspend fun updateLikedBooth(booth: BoothDetailResponse) {
        likedBoothDao.updateLikedBooth(booth.id, booth.isLiked)
    }
}
