package com.unifest.android.core.data.repository

import com.unifest.android.core.network.response.FestivalSearchResponse

interface FestivalRepository {
    suspend fun getAllFestivals(): Result<FestivalSearchResponse>
    suspend fun searchFestival(name: String): Result<FestivalSearchResponse>
}
