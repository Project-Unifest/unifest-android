package com.unifest.android.core.data.repository

import com.unifest.android.core.model.BoothDetailModel

interface BoothRepository {
    suspend fun getPopularBooths(festivalId: Long): Result<List<BoothDetailModel>>
    suspend fun getBoothDetail(boothId: Long): Result<BoothDetailModel>
    suspend fun likeBooth(boothId: Long): Result<Unit>
}
