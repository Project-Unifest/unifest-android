package com.unifest.android.core.data.impl.repository

import com.unifest.android.core.data.api.datasource.DeviceIdDataSource
import com.unifest.android.core.data.api.repository.LikedFestivalRepository
import com.unifest.android.core.data.mapper.toEntity
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.database.LikedFestivalDao
import com.unifest.android.core.datastore.api.RecentLikedFestivalDataSource
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
    private val service: UnifestService,
    private val deviceIdDataSource: DeviceIdDataSource,
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

    override fun getRecentLikedFestivalStream(): Flow<FestivalModel> =
        recentLikedFestivalDataSource.recentLikedFestivalStream
            .map { localLikedFestival ->
                runSuspendCatching {
                    service.searchSchool(
                        name = localLikedFestival.schoolName,
                    ).data.map { it.toModel() }
                }.fold(
                    onSuccess = { festivals ->
                        festivals.find { it.festivalId == localLikedFestival.festivalId }
                            ?.let { remoteFestival ->
                                if (localLikedFestival == remoteFestival) {
                                    localLikedFestival
                                } else {
                                    recentLikedFestivalDataSource.setRecentLikedFestival(remoteFestival)
                                    remoteFestival
                                }
                            } ?: localLikedFestival
                    },
                    onFailure = { localLikedFestival },
                )
            }

    override suspend fun setRecentLikedFestival(festival: FestivalModel) {
        recentLikedFestivalDataSource.setRecentLikedFestival(festival)
    }

    override suspend fun registerLikedFestival(festival: FestivalModel) = runSuspendCatching {
        val deviceId = deviceIdDataSource.getDeviceId()
        service.registerLikedFestival(festival.festivalId, LikedFestivalRequest(deviceId))
    }

    override suspend fun unregisterLikedFestival(festival: FestivalModel) = runSuspendCatching {
        val deviceId = deviceIdDataSource.getDeviceId()
        service.unregisterLikedFestival(festival.festivalId, LikedFestivalRequest(deviceId))
    }
}
