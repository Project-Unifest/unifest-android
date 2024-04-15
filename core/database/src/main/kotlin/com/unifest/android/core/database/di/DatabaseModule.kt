package com.unifest.android.core.database.di

import android.content.Context
import androidx.room.Room
import com.unifest.android.core.database.LikedBoothDatabase
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
    fun provideLikedBoothDatabase(@ApplicationContext context: Context): LikedBoothDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            LikedBoothDatabase::class.java,
            "liked_booth_database",
        ).build()
}
