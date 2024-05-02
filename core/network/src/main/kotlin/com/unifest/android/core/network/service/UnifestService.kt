package com.unifest.android.core.network.service

import com.unifest.android.core.network.request.LikeBoothRequest
import com.unifest.android.core.network.response.BoothDetailResponse
import com.unifest.android.core.network.response.FestivalSearchResponse
import com.unifest.android.core.network.response.FestivalTodayResponse
import com.unifest.android.core.network.response.LikeBoothResponse
import com.unifest.android.core.network.response.PopularBoothsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnifestService {
    @GET("festival/all")
    suspend fun getAllFestivals(): FestivalSearchResponse

    @GET("festival")
    suspend fun searchSchool(
        @Query("name") name: String,
    ): FestivalSearchResponse

    @GET("festival/region")
    suspend fun searchRegion(
        @Query("region") region: String,
    ): FestivalSearchResponse

    @GET("festival/after")
    suspend fun getIncomingFestivals(): FestivalSearchResponse

    @GET("festival/today")
    suspend fun getTodayFestivals(
        @Query("date") date: String,
    ): FestivalTodayResponse

    @GET("api/booths")
    suspend fun getPopularBooths(
        @Query("festival") festivalId: Long,
    ): PopularBoothsResponse

    @GET("api/booths/{booth-id}")
    suspend fun getBoothDetail(
        @Path("booth-id") boothId: Long,
    ): BoothDetailResponse

    @POST("api/likes")
    suspend fun likeBooth(
        @Body likeBoothRequest: LikeBoothRequest,
    ): LikeBoothResponse
}
