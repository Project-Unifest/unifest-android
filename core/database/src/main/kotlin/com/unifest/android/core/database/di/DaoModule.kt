package com.unifest.android.core.database.di

import com.unifest.android.core.database.LikedBoothDao
import com.unifest.android.core.database.LikedBoothDatabase
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
}
