package com.unifest.android.core.data.impl.repository

import com.unifest.android.core.data.api.datasource.DeviceIdDataSource
import com.unifest.android.core.data.api.repository.WaitingRepository
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.network.request.WaitingRequest
import com.unifest.android.core.network.service.UnifestService
import javax.inject.Inject

class DefaultWaitingRepository @Inject constructor(
    private val service: UnifestService,
    private val deviceIdDataSource: DeviceIdDataSource,
) : WaitingRepository {
    override suspend fun getMyWaitingList() = runSuspendCatching {
        val deviceId = deviceIdDataSource.getDeviceId()
        service.getMyWaitingList(deviceId = deviceId).data?.map { it.toModel() } ?: emptyList()
    }

    override suspend fun cancelBoothWaiting(waitingId: Long): Result<Unit> = runSuspendCatching {
        val deviceId = deviceIdDataSource.getDeviceId()
        service.cancelBoothWaiting(WaitingRequest(waitingId = waitingId, deviceId = deviceId)).data.toModel()
    }
}
