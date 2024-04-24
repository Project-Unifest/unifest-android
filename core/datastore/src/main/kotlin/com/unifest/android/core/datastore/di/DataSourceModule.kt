package com.unifest.android.core.datastore.di

import com.unifest.android.core.datastore.OnboardingDataSource
import com.unifest.android.core.datastore.OnboardingDataSourceImpl
import com.unifest.android.core.datastore.RecentLikedFestivalDataSource
import com.unifest.android.core.datastore.RecentLikedFestivalDataSourceImpl
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
}
