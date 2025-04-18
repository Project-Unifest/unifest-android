package com.unifest.android.core.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.unifest.android.core.datastore.api.TokenDataSource
import com.unifest.android.core.datastore.impl.di.TokenDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class DefaultTokenDataSource @Inject constructor(
    @TokenDataStore private val dataStore: DataStore<Preferences>,
) : TokenDataSource {
    private companion object {
        private val KEY_FCM_TOKEN = stringPreferencesKey("fcm_token")
    }

    override suspend fun getFCMToken(): String = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_FCM_TOKEN] ?: ""

    override suspend fun setFCMToken(token: String) {
        dataStore.edit { preferences -> preferences[KEY_FCM_TOKEN] = token }
    }
}
