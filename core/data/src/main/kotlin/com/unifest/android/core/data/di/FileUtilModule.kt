package com.unifest.android.core.data.di

import android.content.Context
import com.unifest.android.core.data.util.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object FileUtilModule {

    @Singleton
    @Provides
    internal fun provideFileUtil(@ApplicationContext context: Context): FileUtil {
        return FileUtil(context)
    }
}
