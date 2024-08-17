package com.unifest.android.core.data.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.google.firebase.remoteconfig.get
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteConfigDataSourceImpl @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
) : RemoteConfigDataSource {
    override suspend fun getValue(key: String): FirebaseRemoteConfigValue? = suspendCoroutine { continuation ->
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(
                    remoteConfig[key].also {
                        Timber.d("FirebaseRemoteConfigValue: ${it.asString()}")
                    },
                )
            } else {
                Timber.e(task.exception, "getValue: $key")
                continuation.resume(null)
            }
        }
    }

    override suspend fun getString(key: String): String? = getValue(key)?.asString()
}
