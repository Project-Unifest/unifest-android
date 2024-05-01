package com.unifest.android.core.data.repository

import com.unifest.android.core.model.FestivalModel
import com.unifest.android.core.model.FestivalTodayModel

interface FestivalRepository {
    suspend fun getAllFestivals(): Result<List<FestivalModel>>
    suspend fun searchSchool(name: String): Result<List<FestivalModel>>
    suspend fun searchRegion(region: String): Result<List<FestivalModel>>
    suspend fun getIncomingFestivals(): Result<List<FestivalModel>>
    suspend fun getTodayFestivals(date: String): Result<List<FestivalTodayModel>>
}
