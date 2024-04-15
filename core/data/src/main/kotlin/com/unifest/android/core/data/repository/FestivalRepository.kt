package com.unifest.android.core.data.repository

import com.unifest.android.core.data.response.FestivalSearchResponse

interface FestivalRepository {
    suspend fun searchFestival(name: String): Result<FestivalSearchResponse>
}
