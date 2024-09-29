package com.unifest.android.core.datastore.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OnboardingDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RecentLikedFestivalDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SettingDataStore
