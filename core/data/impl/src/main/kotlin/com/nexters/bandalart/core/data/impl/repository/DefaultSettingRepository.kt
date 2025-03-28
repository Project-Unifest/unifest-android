package com.nexters.bandalart.core.data.impl.repository

import com.unifest.android.core.datastore.SettingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultSettingRepository @Inject constructor(
    private val settingDataSource: SettingDataSource,
) : com.nexters.bandalart.core.data.api.repository.SettingRepository {
    override fun flowIsClusteringEnabled(): Flow<Boolean> =
        settingDataSource.settingsData.map { settingsData -> settingsData.isClusteringEnabled }

    override suspend fun updateIsClusteringEnabled(isClusteringEnabled: Boolean) {
        settingDataSource.updateIsClusteringEnabled(isClusteringEnabled)
    }
}
