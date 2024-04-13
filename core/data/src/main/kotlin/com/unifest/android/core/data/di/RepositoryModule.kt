package com.unifest.android.core.data.di

import com.unifest.android.core.data.repository.DefaultLikedBoothRepository
import com.unifest.android.core.data.repository.LikedBoothRepository
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
    abstract fun bindLikedBoothRepository(likedBoothRepository: DefaultLikedBoothRepository): LikedBoothRepository
}
