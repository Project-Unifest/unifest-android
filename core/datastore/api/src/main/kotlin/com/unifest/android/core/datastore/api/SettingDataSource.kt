package com.unifest.android.core.datastore.api

import com.unifest.android.core.datastore.api.model.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingDataSource {
    val settingsData: Flow<SettingsData>
    suspend fun updateIsClusteringEnabled(isClusteringEnabled: Boolean)
}
