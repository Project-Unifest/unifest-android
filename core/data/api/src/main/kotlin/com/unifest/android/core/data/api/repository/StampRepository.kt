package com.unifest.android.core.data.api.repository

import com.unifest.android.core.model.StampBoothModel
import com.unifest.android.core.model.StampFestivalModel
import com.unifest.android.core.model.StampRecordModel

interface StampRepository {
    suspend fun getCollectedStamps(festivalId: Long): Result<List<StampRecordModel>>
    suspend fun getStampEnabledBooths(festivalId: Long): Result<List<StampBoothModel>>
    suspend fun registerStamp(boothId: Long, festivalId: Long): Result<Unit>
    suspend fun getStampEnabledFestivals(): Result<List<StampFestivalModel>>
}
