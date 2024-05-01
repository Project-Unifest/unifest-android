package com.unifest.android.core.data.repository

import com.unifest.android.core.data.mapper.toEntity
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.database.LikedBoothDao
import com.unifest.android.core.model.BoothDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class LikedBoothRepositoryImpl @Inject constructor(
    private val likedBoothDao: LikedBoothDao,
) : LikedBoothRepository {
    override fun getLikedBoothList(): Flow<List<BoothDetailModel>> {
        return likedBoothDao.getLikedBoothList().map { likedBooths ->
            likedBooths.map { likedBooth ->
                likedBooth.toModel()
            }
        }
    }

    override suspend fun insertLikedBooth(booth: BoothDetailModel) {
        likedBoothDao.insertLikedBooth(booth.toEntity())
    }

    override suspend fun deleteLikedBooth(booth: BoothDetailModel) {
        likedBoothDao.deleteLikedBooth(booth.toEntity())
    }

    override suspend fun updateLikedBooth(booth: BoothDetailModel) {
        likedBoothDao.updateLikedBooth(booth.id, booth.isLiked)
    }
}
