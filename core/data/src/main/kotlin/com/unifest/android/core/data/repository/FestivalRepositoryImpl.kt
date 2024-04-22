package com.unifest.android.core.data.repository

import com.unifest.android.core.data.mapper.toEntity
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.database.LikedFestivalDao
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import com.unifest.android.core.network.service.UnifestService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FestivalRepositoryImpl @Inject constructor(
    private val service: UnifestService,
    private val likedFestivalDao: LikedFestivalDao,
) : FestivalRepository {

    override fun getLikedFestivals(): Flow<List<FestivalModel>> {
        return likedFestivalDao.getLikedFestivalList().map { likedFestivals ->
            likedFestivals.map { likedFestival ->
                likedFestival.toModel()
            }
        }
    }
    override suspend fun getAllFestivals() = runSuspendCatching {
        service.getAllFestivals().data.map { it.toModel() }
    }

    override suspend fun searchFestival(name: String) = runSuspendCatching {
        service.searchFestival(name).data.map { it.toModel() }
    }

    override suspend fun getIncomingFestivals() = runSuspendCatching {
        service.getIncomingFestivals().data.map { it.toModel() }
    }

    override suspend fun getTodayFestivals(date: String) = runSuspendCatching {
        service.getTodayFestivals(date).data.map { it.toModel() }
    }

    override suspend fun insertLikedFestivalAtHome(festival: FestivalTodayModel) {
        likedFestivalDao.insertLikedFestival(festival.toEntity())
    }

    override suspend fun insertLikedFestivalAtSearch(festival: FestivalModel) {
        likedFestivalDao.insertLikedFestival(festival.toEntity())
    }
    override suspend fun deleteLikedFestival(festival: FestivalModel) {
        likedFestivalDao.deleteLikedFestival(festival.toEntity())
    }
}
