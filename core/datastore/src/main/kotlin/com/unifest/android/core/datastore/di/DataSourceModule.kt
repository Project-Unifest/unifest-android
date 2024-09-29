package com.unifest.android.core.datastore.di

import com.unifest.android.core.datastore.OnboardingDataSource
import com.unifest.android.core.datastore.OnboardingDataSourceImpl
import com.unifest.android.core.datastore.RecentLikedFestivalDataSource
import com.unifest.android.core.datastore.RecentLikedFestivalDataSourceImpl
import com.unifest.android.core.datastore.SettingDataSource
import com.unifest.android.core.datastore.SettingDataSourceImpl
import com.unifest.android.core.datastore.TokenDataSource
import com.unifest.android.core.datastore.TokenDataSourceImpl
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
    abstract fun bindOnboardingDataSource(onboardingDataSourceImpl: OnboardingDataSourceImpl): OnboardingDataSource

    @Binds
    @Singleton
    abstract fun bindRecentLikedFestivalDataSource(recentLikedFestivalDataSourceImpl: RecentLikedFestivalDataSourceImpl): RecentLikedFestivalDataSource

    @Binds
    @Singleton
    abstract fun bindTokenDataSource(tokenDataSourceImpl: TokenDataSourceImpl): TokenDataSource

    @Binds
    @Singleton
    abstract fun bindSettingDataSource(settingDataSourceImpl: SettingDataSourceImpl): SettingDataSource
}
