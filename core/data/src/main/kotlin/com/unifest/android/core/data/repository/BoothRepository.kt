package com.unifest.android.core.data.repository

import com.unifest.android.core.model.BoothModel
import com.unifest.android.core.model.BoothDetailModel

interface BoothRepository {
    suspend fun getPopularBooths(festivalId: Long): Result<List<BoothModel>>
    suspend fun getAllBooths(festivalId: Long): Result<List<BoothModel>>
    suspend fun getBoothDetail(boothId: Long): Result<BoothDetailModel>
    suspend fun likeBooth(boothId: Long): Result<Unit>
}
