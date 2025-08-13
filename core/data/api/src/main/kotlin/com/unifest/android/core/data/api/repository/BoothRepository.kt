package com.unifest.android.core.data.api.repository

import com.unifest.android.core.model.BoothModel
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.BoothTabModel
import com.unifest.android.core.model.MapModel
import com.unifest.android.core.model.WaitingModel

interface BoothRepository {
    suspend fun getPopularBooths(festivalId: Long): Result<List<BoothModel>>
    suspend fun getAllBooths(festivalId: Long): Result<MapModel>
    suspend fun getBoothDetail(boothId: Long): Result<BoothDetailModel>
    suspend fun likeBooth(boothId: Long): Result<Unit>
    suspend fun getBoothLikes(boothId: Long): Result<Int>
    suspend fun checkPinValidation(boothId: Long, pinNumber: String): Result<Long>
    suspend fun requestBoothWaiting(boothId: Long, tel: String, partySize: Long, pinNumber: String): Result<WaitingModel>
    suspend fun getTabBooths(festivalId: Long): Result<List<BoothTabModel>>
}
