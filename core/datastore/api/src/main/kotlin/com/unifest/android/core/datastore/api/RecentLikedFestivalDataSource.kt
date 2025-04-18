package com.unifest.android.core.datastore.api

import com.unifest.android.core.model.FestivalModel
import kotlinx.coroutines.flow.Flow

interface RecentLikedFestivalDataSource {
    val recentLikedFestivalStream: Flow<FestivalModel>
    suspend fun setRecentLikedFestival(festival: FestivalModel)
}
