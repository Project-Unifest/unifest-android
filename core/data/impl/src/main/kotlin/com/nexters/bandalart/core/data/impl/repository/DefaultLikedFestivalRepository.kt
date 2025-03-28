package com.nexters.bandalart.core.data.impl.repository

import com.nexters.bandalart.core.data.api.repository.LikedFestivalRepository
import com.nexters.bandalart.core.data.mapper.toEntity
import com.nexters.bandalart.core.data.mapper.toModel
import com.nexters.bandalart.core.data.util.runSuspendCatching
import com.unifest.android.core.database.LikedFestivalDao
import com.unifest.android.core.datastore.RecentLikedFestivalDataSource
import com.unifest.android.core.datastore.TokenDataSource
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.network.request.LikedFestivalRequest
import com.unifest.android.core.network.service.UnifestService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultLikedFestivalRepository @Inject constructor(
    private val likedFestivalDao: LikedFestivalDao,
    private val recentLikedFestivalDataSource: RecentLikedFestivalDataSource,
    private val tokenDataSource: TokenDataSource,
    private val service: UnifestService,
) : LikedFestivalRepository {
    override fun getLikedFestivals(): Flow<List<FestivalModel>> {
        return likedFestivalDao.getLikedFestivalList().map { likedFestivals ->
            likedFestivals.map { likedFestival ->
                likedFestival.toModel()
            }
        }
    }

    override suspend fun insertLikedFestivalAtHome(festival: FestivalTodayModel) {
        likedFestivalDao.insertLikedFestival(festival.toEntity())
    }

    override suspend fun insertLikedFestivalAtSearch(festival: FestivalModel) {
        likedFestivalDao.insertLikedFestival(festival.toEntity())
    }

    override suspend fun insertLikedFestivals(festivals: List<FestivalModel>) {
        likedFestivalDao.insertLikedFestivals(festivals.map { it.toEntity() })
    }

    override suspend fun deleteLikedFestival(festival: FestivalModel) {
        likedFestivalDao.deleteLikedFestival(festival.toEntity())
    }

    override suspend fun getRecentLikedFestival(): FestivalModel {
        return recentLikedFestivalDataSource.getRecentLikedFestival()
    }

    override suspend fun setRecentLikedFestival(festival: FestivalModel) {
        recentLikedFestivalDataSource.setRecentLikedFestival(festival)
    }

    override suspend fun registerLikedFestival() = runSuspendCatching {
        val festivalId = recentLikedFestivalDataSource.getRecentLikedFestival().festivalId
        val fcmToken = tokenDataSource.getFCMToken()
        service.registerLikedFestival(LikedFestivalRequest(festivalId, fcmToken))
    }

    override suspend fun unregisterLikedFestival() = runSuspendCatching {
        val festivalId = recentLikedFestivalDataSource.getRecentLikedFestival().festivalId
        val fcmToken = tokenDataSource.getFCMToken()
        service.unregisterLikedFestival(LikedFestivalRequest(festivalId, fcmToken))
    }
}
