package com.unifest.android.core.datastore

import com.unifest.android.core.model.FestivalModel

interface RecentLikedFestivalDataSource {
    suspend fun getRecentLikedFestival(): FestivalModel
    suspend fun setRecentLikedFestival(festival: FestivalModel)
}
