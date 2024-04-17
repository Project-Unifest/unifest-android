package com.unifest.android.core.data.repository

import com.unifest.android.core.model.FestivalSearchModel
import com.unifest.android.core.model.FestivalTodayModel

interface FestivalRepository {
    suspend fun getAllFestivals(): Result<List<FestivalSearchModel>>
    suspend fun searchFestival(name: String): Result<List<FestivalSearchModel>>
    suspend fun getIncomingFestivals(): Result<List<FestivalSearchModel>>
    suspend fun getTodayFestivals(date: String): Result<List<FestivalTodayModel>>
}
