package com.unifest.android.core.datastore.di

import com.unifest.android.core.datastore.OnboardingDataSource
import com.unifest.android.core.datastore.DefaultOnboardingDataSource
import com.unifest.android.core.datastore.RecentLikedFestivalDataSource
import com.unifest.android.core.datastore.DefaultRecentLikedFestivalDataSource
import com.unifest.android.core.datastore.SettingDataSource
import com.unifest.android.core.datastore.DefaultSettingDataSource
import com.unifest.android.core.datastore.TokenDataSource
import com.unifest.android.core.datastore.DefaultTokenDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindOnboardingDataSource(defaultOnboardingDataSource: DefaultOnboardingDataSource): OnboardingDataSource

    @Binds
    @Singleton
    abstract fun bindRecentLikedFestivalDataSource(defaultRecentLikedFestivalDataSource: DefaultRecentLikedFestivalDataSource): RecentLikedFestivalDataSource

    @Binds
    @Singleton
    abstract fun bindTokenDataSource(defaultTokenDataSource: DefaultTokenDataSource): TokenDataSource

    @Binds
    @Singleton
    abstract fun bindSettingDataSource(defaultSettingDataSource: DefaultSettingDataSource): SettingDataSource
}
