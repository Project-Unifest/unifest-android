package com.nexters.bandalart.core.data.di

import com.nexters.bandalart.core.data.api.repository.BoothRepository
import com.nexters.bandalart.core.data.api.repository.FestivalRepository
import com.nexters.bandalart.core.data.api.repository.LikedBoothRepository
import com.nexters.bandalart.core.data.api.repository.LikedFestivalRepository
import com.nexters.bandalart.core.data.api.repository.MessagingRepository
import com.nexters.bandalart.core.data.api.repository.OnboardingRepository
import com.nexters.bandalart.core.data.api.repository.RemoteConfigRepository
import com.nexters.bandalart.core.data.api.repository.SettingRepository
import com.nexters.bandalart.core.data.api.repository.StampRepository
import com.nexters.bandalart.core.data.api.repository.WaitingRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultBoothRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultFestivalRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultLikedBoothRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultLikedFestivalRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultMessagingRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultOnboardingRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultRemoteConfigRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultSettingRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultStampRepository
import com.nexters.bandalart.core.data.impl.repository.DefaultWaitingRepository
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
