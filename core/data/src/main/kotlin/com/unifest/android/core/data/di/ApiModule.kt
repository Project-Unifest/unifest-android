package com.unifest.android.core.data.di

import com.unifest.android.core.data.service.UnifestService
import com.unifest.android.core.network.UnifestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    internal fun provideUnifestService(
        @UnifestApi retrofit: Retrofit,
    ): UnifestService {
        return retrofit.create(UnifestService::class.java)
    }
}
