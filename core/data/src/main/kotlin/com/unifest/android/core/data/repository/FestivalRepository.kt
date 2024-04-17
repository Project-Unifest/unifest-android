package com.unifest.android.core.data.repository

import com.unifest.android.core.model.FestivalSearchModel

interface FestivalRepository {
    suspend fun getAllFestivals(): Result<List<FestivalSearchModel>>
    suspend fun searchFestival(name: String): Result<List<FestivalSearchModel>>
    suspend fun getIncomingFestivals(): Result<List<FestivalSearchModel>>
}
