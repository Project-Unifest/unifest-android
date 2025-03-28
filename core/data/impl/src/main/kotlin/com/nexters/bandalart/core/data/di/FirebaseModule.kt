package com.nexters.bandalart.core.data.di

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.unifest.android.core.data.impl.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object FirebaseModule {
    @Singleton
    @Provides
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance().apply {
            val configSettings by lazy {
                remoteConfigSettings {
                    minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 0 else 60
                }
            }
            setConfigSettingsAsync(configSettings)
        }
    }

    @Singleton
    @Provides
    fun provideMessaging(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }
}
