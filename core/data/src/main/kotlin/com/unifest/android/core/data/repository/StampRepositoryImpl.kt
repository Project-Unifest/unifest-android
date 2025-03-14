package com.unifest.android.core.data.repository

import android.content.Context
import com.unifest.android.core.common.getDeviceId
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.datastore.RecentLikedFestivalDataSource
import com.unifest.android.core.network.request.RegisterStampRequest
import com.unifest.android.core.network.service.UnifestService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class StampRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: UnifestService,
    private val recentLikedFestivalDataSource: RecentLikedFestivalDataSource,
) : StampRepository {
    override suspend fun getCollectedStampCount() = runSuspendCatching {
        val deviceId = getDeviceId(context)
        service.getCollectedStampCount(deviceId).data.map { it.toModel()}
    }

    override suspend fun getStampEnabledBoothList() = runSuspendCatching {
        val festivalId = recentLikedFestivalDataSource.getRecentLikedFestivalId()
        service.getStampEnabledBoothList(festivalId).data.map { it.toModel() }
    }

    override suspend fun registerStamp(boothId: Long) = runSuspendCatching {
        val deviceId = getDeviceId(context)
        service.registerStamp(RegisterStampRequest(token = deviceId, boothId = boothId))
    }
}
