package com.unifest.android.core.data.repository

import com.unifest.android.core.model.StampBoothModel

interface StampRepository {
    suspend fun getCollectedStampCount(): Result<Int>
    suspend fun getStampEnabledBoothList(): Result<List<StampBoothModel>>
    suspend fun registerStamp(boothId: Long): Result<Unit>
}
