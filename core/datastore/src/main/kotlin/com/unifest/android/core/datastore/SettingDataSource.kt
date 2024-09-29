package com.unifest.android.core.datastore

import com.unifest.android.core.datastore.model.SettingsData
import kotlinx.coroutines.flow.Flow

interface SettingDataSource {
    val settingsData : Flow<SettingsData>
    suspend fun updateIsClusteringEnabled(isClusteringEnabled: Boolean)
}
