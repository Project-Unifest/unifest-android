package com.unifest.android.core.data.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.google.firebase.remoteconfig.get
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume

class RemoteConfigDataSourceImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
) : RemoteConfigDataSource {
    override suspend fun getValue(key: String): FirebaseRemoteConfigValue? = suspendCancellableCoroutine { continuation ->
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(
                    remoteConfig[key].also {
                        Timber.d("FirebaseRemoteConfigValue: ${it.asString()}")
                    },
                )
            } else {
                Timber.e(task.exception, "getValue: $key")
                if (!continuation.isCompleted) {
                    continuation.resume(null)
                }
            }
        }
    }

    override suspend fun getString(key: String): String? = getValue(key)?.asString()
}
