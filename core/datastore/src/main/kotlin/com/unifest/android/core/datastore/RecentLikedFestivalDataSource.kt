package com.unifest.android.core.datastore

interface RecentLikedFestivalDataSource {
    suspend fun getRecentLikedFestivalName(): String
    suspend fun setRecentLikedFestivalName(festivalName: String)
    suspend fun getRecentLikedFestivalId(): Long
    suspend fun setRecentLikedFestivalId(festivalId: Long)
}
