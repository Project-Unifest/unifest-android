package com.unifest.android.core.data.repository

import kotlinx.coroutines.flow.Flow

interface RemoteConfigRepository {
    fun shouldUpdate(): Flow<Boolean>
}
