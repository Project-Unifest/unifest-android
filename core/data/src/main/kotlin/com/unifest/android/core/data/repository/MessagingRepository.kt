package com.unifest.android.core.data.repository

interface MessagingRepository {
    suspend fun refreshFCMToken(): String?
    suspend fun setFCMToken(token: String)
}
