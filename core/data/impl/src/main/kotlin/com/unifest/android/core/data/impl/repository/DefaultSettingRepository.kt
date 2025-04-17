package com.unifest.android.core.data.impl.repository

import com.unifest.android.core.data.api.repository.SettingRepository
import com.unifest.android.core.datastore.api.SettingDataSource
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
