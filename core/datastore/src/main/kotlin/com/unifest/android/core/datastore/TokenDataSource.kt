package com.unifest.android.core.datastore

interface TokenDataSource {
    suspend fun getFCMToken(): String
    suspend fun setFCMToken(token: String)
}
