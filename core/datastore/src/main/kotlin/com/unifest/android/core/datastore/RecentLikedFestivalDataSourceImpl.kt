package com.unifest.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class RecentLikedFestivalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : RecentLikedFestivalDataSource {
    private companion object {
        private val KEY_RECENT_LIKED_FESTIVAL = stringPreferencesKey("onboarding_complete")
    }

    override suspend fun getRecentLikedFestival(): String = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_RECENT_LIKED_FESTIVAL] ?: ""

    override suspend fun setRecentLikedFestival(schoolName: String) {
        dataStore.edit { preferences -> preferences[KEY_RECENT_LIKED_FESTIVAL] = schoolName }
    }
}
