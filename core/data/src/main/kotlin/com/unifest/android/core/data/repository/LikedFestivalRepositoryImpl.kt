package com.unifest.android.core.data.repository

import com.unifest.android.core.data.mapper.toEntity
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.database.LikedFestivalDao
import com.unifest.android.core.datastore.RecentLikedFestivalDataSource
import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class LikedFestivalRepositoryImpl @Inject constructor(
    private val likedFestivalDao: LikedFestivalDao,
    private val recentLikedFestivalDataSource: RecentLikedFestivalDataSource,
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

    override suspend fun deleteLikedFestival(festival: FestivalModel) {
        likedFestivalDao.deleteLikedFestival(festival.toEntity())
    }

    override suspend fun getRecentLikedFestival(): String {
        return recentLikedFestivalDataSource.getRecentLikedFestival()
    }

    override suspend fun setRecentLikedFestival(schoolName: String) {
        recentLikedFestivalDataSource.setRecentLikedFestival(schoolName)
    }
}
