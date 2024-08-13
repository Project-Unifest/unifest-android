package com.unifest.android.core.data.repository

import com.unifest.android.core.model.BoothModel
import com.unifest.android.core.model.BoothDetailModel
import com.unifest.android.core.model.WaitingModel
import com.unifest.android.core.network.response.Waiting

interface BoothRepository {
    suspend fun getPopularBooths(festivalId: Long): Result<List<BoothModel>>
    suspend fun getAllBooths(festivalId: Long): Result<List<BoothModel>>
    suspend fun getBoothDetail(boothId: Long): Result<BoothDetailModel>
    suspend fun likeBooth(boothId: Long): Result<Unit>
    suspend fun getBoothLikes(boothId: Long): Result<Int>
    suspend fun checkPinValidation(boothId: Long, pinNumber: String): Result<Long>
    suspend fun requestBoothWaiting(boothId: Long,tel: String, partySize:Long, pinNumber: String): Result<WaitingModel>
    //todo: add more methods
}
