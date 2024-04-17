package com.unifest.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class OnboardingDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : OnboardingDataSource {
    private companion object {
        private val KEY_ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
    }

    override suspend fun checkOnboardingCompletion(): Boolean = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_ONBOARDING_COMPLETE] ?: false

    override suspend fun completeOnboarding(flag: Boolean) {
        dataStore.edit { preferences -> preferences[KEY_ONBOARDING_COMPLETE] = flag }
    }
}