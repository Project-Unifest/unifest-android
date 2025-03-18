package com.unifest.android.core.data.repository

import com.unifest.android.core.model.StampBoothModel
import com.unifest.android.core.model.StampRecordModel

interface StampRepository {
    suspend fun getCollectedStamps(): Result<List<StampRecordModel>>
    suspend fun getStampEnabledBoothList(): Result<List<StampBoothModel>>
    suspend fun registerStamp(boothId: Long): Result<Unit>
}
