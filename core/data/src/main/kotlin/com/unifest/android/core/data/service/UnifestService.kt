package com.unifest.android.core.data.service

import com.unifest.android.core.data.response.BoothDetailResponse
import com.unifest.android.core.data.response.FestivalSearchResponse
import com.unifest.android.core.data.response.PopularBoothsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnifestService {
    @GET("festival")
    suspend fun searchFestival(
        @Query("name") name: String,
    ): FestivalSearchResponse

    @GET("api/booths")
    suspend fun getPopularBooths(
        @Query("festival") festivalId: Long,
    ): PopularBoothsResponse

    @GET("api/booths/{booth-id}")
    suspend fun getBoothDetail(
        @Path("booth-id") boothId: Long,
    ): BoothDetailResponse
}
