package com.unifest.android.core.data.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

interface RemoteConfigDataSource {
    suspend fun getValue(key: String): FirebaseRemoteConfigValue?
    suspend fun getString(key: String): String?
}
