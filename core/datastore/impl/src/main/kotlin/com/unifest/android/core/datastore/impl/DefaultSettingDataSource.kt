package com.unifest.android.core.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.unifest.android.core.datastore.api.SettingDataSource
import com.unifest.android.core.datastore.api.model.SettingsData
import com.unifest.android.core.datastore.impl.di.SettingDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultSettingDataSource @Inject constructor(
    @SettingDataStore private val dataStore: DataStore<Preferences>,
) : SettingDataSource {
    private companion object PreferencesKey {
        private val KEY_IS_CLUSTERING_ENABLED = booleanPreferencesKey("is_clustering_enabled")
    }

    override val settingsData = dataStore.data.map { preferences ->
        SettingsData(
            isClusteringEnabled = preferences[KEY_IS_CLUSTERING_ENABLED] ?: true,
        )
    }

    override suspend fun updateIsClusteringEnabled(isClusteringEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_IS_CLUSTERING_ENABLED] = isClusteringEnabled
        }
    }
}
