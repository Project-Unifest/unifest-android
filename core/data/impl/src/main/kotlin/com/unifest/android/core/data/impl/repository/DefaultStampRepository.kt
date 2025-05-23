package com.unifest.android.core.data.impl.repository

import android.content.Context
import com.unifest.android.core.data.api.repository.StampRepository
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.common.getDeviceId
import com.unifest.android.core.network.request.RegisterStampRequest
import com.unifest.android.core.network.service.UnifestService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class DefaultStampRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: UnifestService,
) : StampRepository {
    override suspend fun getCollectedStamps(festivalId: Long) = runSuspendCatching {
        val deviceId = getDeviceId(context)
        service.getCollectedStamps(deviceId, festivalId).data.map { it.toModel() }
    }

    override suspend fun getStampEnabledBooths(festivalId: Long) = runSuspendCatching {
        service.getStampEnabledBooths(festivalId).data.map { it.toModel() }
    }

    override suspend fun registerStamp(boothId: Long, festivalId: Long) = runSuspendCatching {
        val deviceId = getDeviceId(context)
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
