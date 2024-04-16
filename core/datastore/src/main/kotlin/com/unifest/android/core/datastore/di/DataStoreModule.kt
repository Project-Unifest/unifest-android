package com.unifest.android.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val ONBOARDING_DATASTORE = "onboarding_datastore"
private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = ONBOARDING_DATASTORE)

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Singleton
    @Provides
    internal fun provideOnboardingDataStore(@ApplicationContext context: Context) = context.onboardingDataStore
}
