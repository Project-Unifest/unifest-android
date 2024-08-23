package com.unifest.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.unifest.android.core.datastore.di.RecentLikedFestivalDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class RecentLikedFestivalDataSourceImpl @Inject constructor(
    @RecentLikedFestivalDataStore private val dataStore: DataStore<Preferences>,
) : RecentLikedFestivalDataSource {
    private companion object {
        private val KEY_RECENT_LIKED_FESTIVAL = stringPreferencesKey("recent_liked_festival")
        private val KEY_RECENT_LIKED_FESTIVAL_ID = longPreferencesKey("recent_liked_festival_id")
    }

    override suspend fun getRecentLikedFestivalName(): String = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_RECENT_LIKED_FESTIVAL] ?: ""

    override suspend fun setRecentLikedFestivalName(festivalName: String) {
        dataStore.edit { preferences -> preferences[KEY_RECENT_LIKED_FESTIVAL] = festivalName }
    }

    override suspend fun getRecentLikedFestivalId(): Long = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_RECENT_LIKED_FESTIVAL_ID] ?: 0L

    override suspend fun setRecentLikedFestivalId(festivalId: Long) {
        dataStore.edit { preferences -> preferences[KEY_RECENT_LIKED_FESTIVAL_ID] = festivalId }
    }
}
