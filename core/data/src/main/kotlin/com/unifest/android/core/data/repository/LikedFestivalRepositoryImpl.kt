package com.unifest.android.core.data.repository

import com.unifest.android.core.datastore.RecentLikedFestivalDataSource
import javax.inject.Inject

class LikedFestivalRepositoryImpl @Inject constructor(
    private val recentLikedFestivalDataSource: RecentLikedFestivalDataSource,
): LikedFestivalRepository {
    override suspend fun getRecentLikedFestival(): String {
        return recentLikedFestivalDataSource.getRecentLikedFestival()
    }

    override suspend fun setRecentLikedFestival(schoolName: String) {
        recentLikedFestivalDataSource.setRecentLikedFestival(schoolName)
    }
}
