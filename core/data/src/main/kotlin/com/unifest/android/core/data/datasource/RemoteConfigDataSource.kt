package com.unifest.android.core.data.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

interface RemoteConfigDataSource {
    suspend fun getValue(key: String): FirebaseRemoteConfigValue?
    suspend fun getString(key: String): String?
    suspend fun getString(key: String, defaultValue: String): String
    suspend fun getLong(key: String): Long?
    suspend fun getLong(key: String, defaultValue: Long): Long

    suspend fun getBoolean(key: String): Boolean?
    suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean

    suspend fun getDouble(key: String): Double?
    suspend fun getDouble(key: String, defaultValue: Double): Double
}
