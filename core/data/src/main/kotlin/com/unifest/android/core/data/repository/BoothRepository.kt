package com.unifest.android.core.data.repository

import com.unifest.android.core.model.BoothDetailModel

interface BoothRepository {
    suspend fun getPopularBooths(festivalId: Long): Result<List<BoothDetailModel>> //todo:Long인지 int인지 확인
    suspend fun getBoothDetail(boothId: Long): Result<BoothDetailModel>
}
