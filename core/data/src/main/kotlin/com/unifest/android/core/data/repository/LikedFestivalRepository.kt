package com.unifest.android.core.data.repository

import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import kotlinx.coroutines.flow.Flow

interface LikedFestivalRepository {
    fun getLikedFestivals(): Flow<List<FestivalModel>>
    suspend fun insertLikedFestivalAtHome(festival: FestivalTodayModel)
    suspend fun insertLikedFestivalAtSearch(festival: FestivalModel)
    suspend fun insertLikedFestivals(festivals: List<FestivalModel>)
    suspend fun deleteLikedFestival(festival: FestivalModel)
    suspend fun getRecentLikedFestival(): FestivalModel
    suspend fun setRecentLikedFestival(festival: FestivalModel)
    suspend fun registerLikedFestival(): Result<Unit>
    suspend fun unregisterLikedFestival(): Result<Unit>
}
