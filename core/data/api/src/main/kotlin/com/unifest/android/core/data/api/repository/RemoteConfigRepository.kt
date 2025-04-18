package com.unifest.android.core.data.api.repository

import kotlinx.coroutines.flow.Flow

interface RemoteConfigRepository {
    fun shouldUpdate(): Flow<Boolean>
}
