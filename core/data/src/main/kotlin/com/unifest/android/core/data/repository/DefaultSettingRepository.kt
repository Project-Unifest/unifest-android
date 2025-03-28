package com.unifest.android.core.data.repository

import com.unifest.android.core.datastore.SettingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultSettingRepository @Inject constructor(
    private val settingDataSource: SettingDataSource,
) : SettingRepository {
    override fun flowIsClusteringEnabled(): Flow<Boolean> =
        settingDataSource.settingsData.map { settingsData -> settingsData.isClusteringEnabled }

    override suspend fun updateIsClusteringEnabled(isClusteringEnabled: Boolean) {
        settingDataSource.updateIsClusteringEnabled(isClusteringEnabled)
    }
}
