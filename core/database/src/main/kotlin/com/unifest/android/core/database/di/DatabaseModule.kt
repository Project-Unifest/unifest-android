package com.unifest.android.core.database.di

import android.content.Context
import androidx.room.Room
import com.unifest.android.core.database.InterestedBoothDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideInterestedBoothDatabase(@ApplicationContext context: Context): InterestedBoothDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            InterestedBoothDatabase::class.java,
            "interested_booth_database",
        ).build()
}
