package com.unifest.android.core.data.di

import com.unifest.android.core.data.repository.BoothRepository
import com.unifest.android.core.data.repository.DefaultBoothRepository
import com.unifest.android.core.data.repository.FestivalRepository
import com.unifest.android.core.data.repository.DefaultFestivalRepository
import com.unifest.android.core.data.repository.DefaultLikedBoothRepository
import com.unifest.android.core.data.repository.LikedBoothRepository
import com.unifest.android.core.data.repository.LikedFestivalRepository
import com.unifest.android.core.data.repository.DefaultLikedFestivalRepository
import com.unifest.android.core.data.repository.MessagingRepository
import com.unifest.android.core.data.repository.DefaultMessagingRepository
import com.unifest.android.core.data.repository.OnboardingRepository
import com.unifest.android.core.data.repository.DefaultOnboardingRepository
import com.unifest.android.core.data.repository.RemoteConfigRepository
import com.unifest.android.core.data.repository.DefaultRemoteConfigRepository
import com.unifest.android.core.data.repository.SettingRepository
import com.unifest.android.core.data.repository.DefaultSettingRepository
import com.unifest.android.core.data.repository.StampRepository
import com.unifest.android.core.data.repository.DefaultStampRepository
import com.unifest.android.core.data.repository.WaitingRepository
import com.unifest.android.core.data.repository.DefaultWaitingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFestivalRepository(defaultFestivalRepository: DefaultFestivalRepository): FestivalRepository

    @Binds
    @Singleton
    abstract fun bindBoothRepository(defaultBoothRepository: DefaultBoothRepository): BoothRepository

    @Binds
    @Singleton
    abstract fun bindLikedFestivalRepository(defaultLikedFestivalRepository: DefaultLikedFestivalRepository): LikedFestivalRepository

    @Binds
    @Singleton
    abstract fun bindLikedBoothRepository(defaultLikedBoothRepository: DefaultLikedBoothRepository): LikedBoothRepository

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(defaultOnboardingRepository: DefaultOnboardingRepository): OnboardingRepository

    @Binds
    @Singleton
    abstract fun bindRemoteConfigRepository(defaultRemoteConfigRepository: DefaultRemoteConfigRepository): RemoteConfigRepository

    @Binds
    @Singleton
    abstract fun bingWaitingRepository(defaultWaitingRepository: DefaultWaitingRepository): WaitingRepository

    @Binds
    @Singleton
    abstract fun bindMessagingRepository(defaultMessagingRepository: DefaultMessagingRepository): MessagingRepository

    @Binds
    @Singleton
    abstract fun bindSettingRepository(defaultSettingRepository: DefaultSettingRepository): SettingRepository

    @Binds
    @Singleton
    abstract fun bindStampRepository(defaultStampRepository: DefaultStampRepository): StampRepository
}
