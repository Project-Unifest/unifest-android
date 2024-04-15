package com.unifest.android.core.data.repository

import com.unifest.android.core.data.response.BoothDetailResponse
import com.unifest.android.core.data.response.PopularBoothsResponse

interface BoothRepository {
    suspend fun getPopularBooths(festivalId: Long): Result<PopularBoothsResponse>
    suspend fun getBoothDetail(boothId: Long): Result<BoothDetailResponse>
}
