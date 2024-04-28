package com.unifest.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.unifest.android.core.datastore.di.OnboardingDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class OnboardingDataSourceImpl @Inject constructor(
    @OnboardingDataStore private val dataStore: DataStore<Preferences>,
) : OnboardingDataSource {
    private companion object {
        private val KEY_INTRO_COMPLETE = booleanPreferencesKey("intro_complete")
        private val KEY_MAP_ONBOARDING_COMPLETE = booleanPreferencesKey("map_onboarding_complete")
        private val KEY_FESTIVAL_ONBOARDING_COMPLETE = booleanPreferencesKey("festival_onboarding_complete")
    }

    override suspend fun checkIntroCompletion(): Boolean = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_INTRO_COMPLETE] ?: false

    override suspend fun completeIntro(flag: Boolean) {
        dataStore.edit { preferences -> preferences[KEY_INTRO_COMPLETE] = flag }
    }

    override suspend fun checkMapOnboardingCompletion(): Boolean = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_MAP_ONBOARDING_COMPLETE] ?: false

    override suspend fun completeMapOnboarding(flag: Boolean) {
        dataStore.edit { preferences -> preferences[KEY_MAP_ONBOARDING_COMPLETE] = flag }
    }

    override suspend fun checkFestivalOnboardingCompletion(): Boolean = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_FESTIVAL_ONBOARDING_COMPLETE] ?: false

    override suspend fun completeFestivalOnboarding(flag: Boolean) {
        dataStore.edit { preferences -> preferences[KEY_FESTIVAL_ONBOARDING_COMPLETE] = flag }
    }
}
