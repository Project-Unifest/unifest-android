package com.unifest.android.core.network.di

import com.unifest.android.core.network.service.UnifestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    internal fun provideUnifestService(
        retrofit: Retrofit,
    ): UnifestService {
        return retrofit.create()
    }
}
