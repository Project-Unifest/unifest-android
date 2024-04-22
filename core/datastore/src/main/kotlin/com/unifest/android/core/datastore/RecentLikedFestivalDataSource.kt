package com.unifest.android.core.datastore

interface RecentLikedFestivalDataSource {
    suspend fun getRecentLikedFestival(): String
    suspend fun setRecentLikedFestival(schoolName: String)
}
