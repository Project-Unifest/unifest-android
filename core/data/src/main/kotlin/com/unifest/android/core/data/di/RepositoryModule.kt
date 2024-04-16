package com.unifest.android.core.data.di

import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.BoothRepositoryImpl
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.FestivalRepositoryImpl
import com.unifest.android.core.data.repository.LikedBoothRepositoryImpl
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.core.data.repository.OnboardingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFestivalRepository(festivalRepositoryImpl: FestivalRepositoryImpl): FestivalRepository

    @Binds
    @Singleton
    abstract fun bindBoothRepository(boothRepositoryImpl: BoothRepositoryImpl): BoothRepository

    @Binds
    @Singleton
    abstract fun bindLikedBoothRepository(likedBoothRepositoryImpl: LikedBoothRepositoryImpl): LikedBoothRepository

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(onboardingRepositoryImpl: OnboardingRepositoryImpl): OnboardingRepository
}
