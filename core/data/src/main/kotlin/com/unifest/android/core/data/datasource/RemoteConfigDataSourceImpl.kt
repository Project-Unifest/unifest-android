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
    override suspend fun getString(key: String, defaultValue: String): String = getValue(key)?.asString() ?: defaultValue

    override suspend fun getLong(key: String): Long? = getValue(key)?.asLong()
    override suspend fun getLong(key: String, defaultValue: Long): Long = getValue(key)?.asLong() ?: defaultValue

    override suspend fun getBoolean(key: String): Boolean? = getValue(key)?.asBoolean()
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean = getValue(key)?.asBoolean() ?: defaultValue

    override suspend fun getDouble(key: String): Double? = getValue(key)?.asDouble()
    override suspend fun getDouble(key: String, defaultValue: Double): Double = getValue(key)?.asDouble() ?: defaultValue
}
