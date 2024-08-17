package com.unifest.android.core.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.unifest.android.core.datastore.TokenDataSource
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MessagingRepositoryImpl @Inject constructor(
    private val messaging: FirebaseMessaging,
    private val tokenDataSource: TokenDataSource,
): MessagingRepository {
    override suspend fun refreshFCMToken(): String? = suspendCoroutine { continuation ->
        messaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(
                    task.result.also {
                        Timber.d("FCM registration token: $it")
                    },
                )
            } else {
                Timber.e(task.exception)
                continuation.resume(null)
            }
        }
    }

    override suspend fun setFCMToken(token: String) {
        tokenDataSource.setFCMToken(token)
    }
}
