package com.unifest.android.core.data.impl.repository

import com.unifest.android.core.data.api.datasource.DeviceIdDataSource
import com.unifest.android.core.data.api.repository.StampRepository
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.network.request.RegisterStampRequest
import com.unifest.android.core.network.service.UnifestService
import javax.inject.Inject

internal class DefaultStampRepository @Inject constructor(
    private val service: UnifestService,
    private val deviceIdDataSource: DeviceIdDataSource,
) : StampRepository {
    override suspend fun getCollectedStamps(festivalId: Long) = runSuspendCatching {
        val deviceId = deviceIdDataSource.getDeviceId()
        service.getCollectedStamps(deviceId, festivalId).data.map { it.toModel() }
    }

    override suspend fun getStampEnabledBooths(festivalId: Long) = runSuspendCatching {
        service.getStampEnabledBooths(festivalId).data.map { it.toModel() }
    }

    override suspend fun registerStamp(boothId: Long, festivalId: Long) = runSuspendCatching {
        val deviceId = deviceIdDataSource.getDeviceId()
        service.registerStamp(
            RegisterStampRequest(
                deviceId = deviceId,
                boothId = boothId,
                festivalId = festivalId,
            ),
        )
    }

    override suspend fun getStampEnabledFestivals() = runSuspendCatching {
        service.getStampEnabledFestivals().data.map { it.toModel() }
    }
}
