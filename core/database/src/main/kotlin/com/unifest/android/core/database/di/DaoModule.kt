package com.unifest.android.core.database.di

import com.unifest.android.core.database.InterestedBoothDao
import com.unifest.android.core.database.InterestedBoothDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideInterestedBoothDao(
        database: InterestedBoothDatabase,
    ): InterestedBoothDao = database.interestedBoothDao()
}
