package com.unifest.android.core.database.di

import com.unifest.android.core.database.LikedBoothDao
import com.unifest.android.core.database.LikedBoothDatabase
import com.unifest.android.core.database.LikedFestivalDao
import com.unifest.android.core.database.LikedFestivalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideLikedBoothDao(
        database: LikedBoothDatabase,
    ): LikedBoothDao = database.likedBoothDao()

    @Provides
    fun provideLikedFestivalDao(
        database: LikedFestivalDatabase,
    ): LikedFestivalDao = database.likedFestivalDao()
}
