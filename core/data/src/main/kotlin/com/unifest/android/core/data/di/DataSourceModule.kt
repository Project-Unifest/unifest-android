package com.unifest.android.core.data.di

import com.unifest.android.core.data.datasource.RemoteConfigDataSource
import com.unifest.android.core.data.datasource.DefaultRemoteConfigDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRemoteConfigDataSource(defaultRemoteConfigDataSource: DefaultRemoteConfigDataSource): RemoteConfigDataSource
}
