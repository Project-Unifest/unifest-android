package com.unifest.android.core.data.impl.repository

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.unifest.android.core.data.api.repository.WaitingRepository
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.common.getDeviceId
import com.unifest.android.core.network.request.WaitingRequest
import com.unifest.android.core.network.service.UnifestService
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class DefaultWaitingRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: UnifestService,
    private val firebaseMessaging: FirebaseMessaging,
) : WaitingRepository {
    override suspend fun getMyWaitingList() = runSuspendCatching {
        val deviceId = getDeviceId(context)
        service.getMyWaitingList(deviceId = deviceId).data?.map { it.toModel() } ?: emptyList()
    }

    override suspend fun cancelBoothWaiting(waitingId: Long): Result<Unit> = runSuspendCatching {
        val deviceId = getDeviceId(context)
        service.cancelBoothWaiting(WaitingRequest(waitingId = waitingId, deviceId = deviceId)).data.toModel()
    }

    override suspend fun registerFCMTopic(waitingId: String) {
        firebaseMessaging.subscribeToTopic("waiting_$waitingId")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Subscribed to topic successfully")
                } else {
                    Timber.e("Failed to subscribe to topic")
                }
            }
    }
}
