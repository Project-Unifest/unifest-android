package com.unifest.android.core.network.service

import com.unifest.android.core.network.request.BoothWaitingRequest
import com.unifest.android.core.network.request.CheckPinValidationRequest
import com.unifest.android.core.network.request.LikeBoothRequest
import com.unifest.android.core.network.request.LikedFestivalRequest
import com.unifest.android.core.network.response.AllBoothsResponse
import com.unifest.android.core.network.response.BoothDetailResponse
import com.unifest.android.core.network.response.CheckPinValidationResponse
import com.unifest.android.core.network.response.FestivalSearchResponse
import com.unifest.android.core.network.response.FestivalTodayResponse
import com.unifest.android.core.network.response.LikeBoothResponse
import com.unifest.android.core.network.response.LikedBoothsResponse
import com.unifest.android.core.network.response.PopularBoothsResponse
import com.unifest.android.core.network.response.WaitingResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
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
        @Query("festivalId") festivalId: Long,
    ): PopularBoothsResponse

    @GET("api/booths/{festival-id}/booths")
    suspend fun getAllBooths(
        @Path("festival-id") festivalId: Long,
    ): AllBoothsResponse

    @GET("api/booths/{booth-id}")
    suspend fun getBoothDetail(
        @Path("booth-id") boothId: Long,
    ): BoothDetailResponse

    @POST("api/likes")
    suspend fun likeBooth(
        @Body likeBoothRequest: LikeBoothRequest,
    ): LikeBoothResponse

    @GET("api/likes")
    suspend fun getLikedBooths(
        @Query("token") token: String,
    ): LikedBoothsResponse

    @GET("api/likes/{booth-id}")
    suspend fun getBoothLikes(
        @Path("booth-id") boothId: Long,
    ): LikeBoothResponse

    @POST("waiting/pin/check")
    suspend fun checkPinValidation(
        @Body checkPinValidationRequest: CheckPinValidationRequest,
    ): CheckPinValidationResponse

    @POST("waiting")
    suspend fun requestBoothWaiting(
        @Body boothWaitingRequest: BoothWaitingRequest,
    ): WaitingResponse

    @POST("booths/{festival-id}/interest")
    suspend fun registerLikedFestival(
        @Path("festival-id") festivalId: Long,
        @Body likedFestivalRequest: LikedFestivalRequest,
    )

    @DELETE("booths/{festival-id}/interest")
    suspend fun unregisterLikedFestival(
        @Path("festival-id") festivalId: Long,
        @Body likedFestivalRequest: LikedFestivalRequest,
    )
}
