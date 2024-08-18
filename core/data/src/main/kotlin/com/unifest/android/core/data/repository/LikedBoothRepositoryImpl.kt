package com.unifest.android.core.data.repository

import android.content.Context
import com.unifest.android.core.common.getDeviceId
import com.unifest.android.core.data.mapper.toEntity
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.database.LikedBoothDao
import com.unifest.android.core.datastore.RecentLikedFestivalDataSource
import com.unifest.android.core.datastore.TokenDataSource
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.network.request.LikedFestivalRequest
import com.unifest.android.core.network.service.UnifestService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class LikedBoothRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: UnifestService,
    private val likedBoothDao: LikedBoothDao,
) : LikedBoothRepository {
    override suspend fun getLikedBooths() = runSuspendCatching {
        service.getLikedBooths(getDeviceId(context)).data.map { it.toModel() }
    }

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

    override suspend fun isLikedBooth(booth: BoothDetailModel): Boolean {
        return likedBoothDao.isLikedBooth(booth.id)
    }
}
