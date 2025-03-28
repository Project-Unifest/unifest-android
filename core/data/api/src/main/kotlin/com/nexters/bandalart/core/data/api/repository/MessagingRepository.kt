package com.nexters.bandalart.core.data.api.repository

import com.unifest.android.core.model.FCMTokenModel

interface MessagingRepository {
    suspend fun refreshFCMToken(): String?
    suspend fun setFCMToken(fcmToken: String)
    suspend fun registerFCMToken(fcmToken: String): Result<FCMTokenModel>
}
