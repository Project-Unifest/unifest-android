package com.unifest.android.core.data.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun flowIsClusteringEnabled(): Flow<Boolean>
    suspend fun updateIsClusteringEnabled(isClusteringEnabled: Boolean)
}
