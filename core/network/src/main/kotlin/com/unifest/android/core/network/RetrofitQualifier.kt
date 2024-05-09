package com.unifest.android.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnifestClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnifestApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FileApi
