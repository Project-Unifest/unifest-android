package com.unifest.android.core.data.repository

interface LikedFestivalRepository {
    suspend fun getRecentLikedFestival(): String
    suspend fun setRecentLikedFestival(schoolName: String)
}
