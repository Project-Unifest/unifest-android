package com.unifest.android.core.data.repository

import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel
import kotlinx.coroutines.flow.Flow

interface FestivalRepository {
    suspend fun getAllFestivals(): Result<List<FestivalModel>>
    suspend fun searchFestival(name: String): Result<List<FestivalModel>>
    suspend fun getIncomingFestivals(): Result<List<FestivalModel>>
    suspend fun getTodayFestivals(date: String): Result<List<FestivalTodayModel>>
    fun getLikedFestivals(): Flow<List<FestivalModel>>
    suspend fun isFestivalExists(festivalId: Int): Boolean
    suspend fun insertLikedFestivalAtHome(festival: FestivalTodayModel)
    suspend fun insertLikedFestivalAtSearch(festival: FestivalModel)
    suspend fun deleteLikedFestivalAtHome(festival: FestivalTodayModel)
}
