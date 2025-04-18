package com.unifest.android.core.datastore.api

interface TokenDataSource {
    suspend fun getFCMToken(): String
    suspend fun setFCMToken(token: String)
}
