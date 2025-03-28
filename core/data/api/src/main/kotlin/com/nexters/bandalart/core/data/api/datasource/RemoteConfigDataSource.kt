package com.nexters.bandalart.core.data.api.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

interface RemoteConfigDataSource {
    suspend fun getValue(key: String): FirebaseRemoteConfigValue?
    suspend fun getString(key: String): String?
}
