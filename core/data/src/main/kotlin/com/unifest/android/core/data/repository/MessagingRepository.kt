package com.unifest.android.core.data.repository

import com.unifest.android.core.network.response.FCMTokenResponse

interface MessagingRepository {
    suspend fun refreshFCMToken(): String?
    suspend fun setFCMToken(fcmToken: String)
    suspend fun registerFCMToken(fcmToken: String): Result<FCMTokenResponse>
}
