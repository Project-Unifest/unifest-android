package com.unifest.android.core.data.repository

import com.unifest.android.core.data.mapper.toEntity
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.database.LikedBoothDao
import com.unifest.android.core.model.BoothDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LikedBoothRepositoryImpl @Inject constructor(
    private val likedBoothDao: LikedBoothDao,
) : LikedBoothRepository {
    override fun getLikedBoothList(): Flow<List<BoothDetail>> {
        return likedBoothDao.getLikedBoothList().map { likedBooths ->
            likedBooths.map { likedBooth ->
                likedBooth.toModel()
            }
        }
    }

    override suspend fun insertLikedBooth(booth: BoothDetail) {
        likedBoothDao.insertLikedBooth(booth.toEntity())
    }

    override suspend fun deleteLikedBooth(booth: BoothDetail) {
        likedBoothDao.deleteLikedBooth(booth.toEntity())
    }

    override suspend fun updateLikedBooth(booth: BoothDetail) {
        likedBoothDao.updateLikedBooth(booth.id, booth.isLiked)
    }
}
