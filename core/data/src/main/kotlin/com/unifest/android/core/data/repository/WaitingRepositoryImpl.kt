package com.unifest.android.core.data.repository

import android.content.Context
import com.unifest.android.core.common.getDeviceId
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.network.request.BoothWaitingRequest
import com.unifest.android.core.network.request.WaitingRequest
import com.unifest.android.core.network.service.UnifestService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WaitingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: UnifestService,
) : WaitingRepository {
    override suspend fun getMyWaitingList() = runSuspendCatching {
        service.getMyWaitingList(
            deviceId = getDeviceId(context),
        ).data?.map { it.toModel() } ?: emptyList()
    }

    override suspend fun cancelBoothWaiting(waitingId:Long): Result<Unit> = runSuspendCatching {
        service.cancelBoothWaiting(
            WaitingRequest(
                waitingId = waitingId,
                deviceId = getDeviceId(context),
            ),
        ).data.toModel()


    }
}
