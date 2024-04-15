package com.unifest.android.core.data.repository

import com.unifest.android.core.network.response.BoothDetailResponse
import com.unifest.android.core.network.response.PopularBoothsResponse

interface BoothRepository {
    suspend fun getPopularBooths(festivalId: Long): Result<PopularBoothsResponse>
    suspend fun getBoothDetail(boothId: Long): Result<BoothDetailResponse>
}
