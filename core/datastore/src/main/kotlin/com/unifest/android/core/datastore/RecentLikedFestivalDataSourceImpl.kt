package com.unifest.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.unifest.android.core.datastore.di.RecentLikedFestivalDataStore
import com.unifest.android.core.model.FestivalModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class RecentLikedFestivalDataSourceImpl @Inject constructor(
    @RecentLikedFestivalDataStore private val dataStore: DataStore<Preferences>,
) : RecentLikedFestivalDataSource {
    private companion object {
        private val KEY_RECENT_LIKED_FESTIVAL = stringPreferencesKey("recent_liked_festival")
        private val json = Json { ignoreUnknownKeys = true }
    }

    override suspend fun getRecentLikedFestival(): FestivalModel = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .first()
        .let { preferences ->
            val festivalJson = preferences[KEY_RECENT_LIKED_FESTIVAL] ?: ""
            if (festivalJson.isEmpty()) {
                FestivalModel()
            } else {
                try {
                    json.decodeFromString<FestivalModel>(festivalJson)
                } catch (e: Exception) {
                    FestivalModel()
                }
            }
        }

    override suspend fun setRecentLikedFestival(festival: FestivalModel) {
        dataStore.edit { preferences ->
            val festivalJson = json.encodeToString(festival)
            preferences[KEY_RECENT_LIKED_FESTIVAL] = festivalJson
        }
    }
}
