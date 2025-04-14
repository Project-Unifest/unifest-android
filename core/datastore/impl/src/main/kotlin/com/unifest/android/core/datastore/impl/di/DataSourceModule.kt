package com.unifest.android.core.datastore.impl.di

import com.unifest.android.core.datastore.api.OnboardingDataSource
import com.unifest.android.core.datastore.api.RecentLikedFestivalDataSource
import com.unifest.android.core.datastore.api.SettingDataSource
import com.unifest.android.core.datastore.api.TokenDataSource
import com.unifest.android.core.datastore.impl.DefaultOnboardingDataSource
import com.unifest.android.core.datastore.impl.DefaultRecentLikedFestivalDataSource
import com.unifest.android.core.datastore.impl.DefaultSettingDataSource
import com.unifest.android.core.datastore.impl.DefaultTokenDataSource
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
