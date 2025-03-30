package com.unifest.android.core.data.impl.repository

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.unifest.android.core.data.api.repository.MessagingRepository
import com.unifest.android.core.data.mapper.toModel
import com.unifest.android.core.data.util.runSuspendCatching
import com.unifest.android.core.common.getDeviceId
import com.unifest.android.core.datastore.TokenDataSource
import com.unifest.android.core.network.request.RegisterFCMTokenRequest
import com.unifest.android.core.network.service.UnifestService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultMessagingRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseMessaging: FirebaseMessaging,
    private val tokenDataSource: TokenDataSource,
    private val service: UnifestService,
) : MessagingRepository {
    override suspend fun refreshFCMToken(): String? = suspendCancellableCoroutine { continuation ->
        firebaseMessaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(
                    task.result.also {
                        Timber.d("FCM registration token: $it")
                    },
                )
            } else {
                Timber.e(task.exception)
                if (!continuation.isCompleted) {
                    continuation.resume(null)
                }
            }
        }
    }

    override suspend fun setFCMToken(fcmToken: String) {
        tokenDataSource.setFCMToken(fcmToken)
    }

    override suspend fun registerFCMToken(fcmToken: String) = runSuspendCatching {
        val deviceId = getDeviceId(context)
        service.registerFCMToken(
            RegisterFCMTokenRequest(
                deviceId = deviceId,
                fcmToken = fcmToken,
            ),
        ).toModel()
    }
}
