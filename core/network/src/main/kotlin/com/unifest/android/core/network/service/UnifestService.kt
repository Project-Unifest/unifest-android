package com.unifest.android.core.network.service

import com.unifest.android.core.network.request.BoothWaitingRequest
import com.unifest.android.core.network.request.CheckPinValidationRequest
import com.unifest.android.core.network.request.LikeBoothRequest
import com.unifest.android.core.network.request.LikedFestivalRequest
import com.unifest.android.core.network.request.RegisterStampRequest
import com.unifest.android.core.network.request.WaitingRequest
import com.unifest.android.core.network.response.booth.AllBoothsResponse
import com.unifest.android.core.network.response.booth.BoothDetailResponse
import com.unifest.android.core.network.response.CheckPinValidationResponse
import com.unifest.android.core.network.response.FestivalSearchResponse
import com.unifest.android.core.network.response.FestivalTodayResponse
import com.unifest.android.core.network.response.booth.LikeBoothResponse
import com.unifest.android.core.network.response.booth.LikedBoothsResponse
import com.unifest.android.core.network.response.MyWaitingResponse
import com.unifest.android.core.network.response.booth.PopularBoothsResponse
import com.unifest.android.core.network.response.CollectedStampCountResponse
import com.unifest.android.core.network.response.WaitingResponse
import com.unifest.android.core.network.response.booth.StampBoothsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UnifestService {
    // 축제 전체 검색
    @GET("festival/all")
    suspend fun getAllFestivals(): FestivalSearchResponse

    // 학교명 검색
    @GET("festival")
    suspend fun searchSchool(
        @Query("name") name: String,
    ): FestivalSearchResponse

    // 지역별 검색
    @GET("festival/region")
    suspend fun searchRegion(
        @Query("region") region: String,
    ): FestivalSearchResponse

    // 다가오는 축제 일정 조회
    @GET("festival/after")
    suspend fun getIncomingFestivals(): FestivalSearchResponse

    // 오늘의 축제 일정 조회
    @GET("festival/today")
    suspend fun getTodayFestivals(
        @Query("date") date: String,
    ): FestivalTodayResponse

    // 상위 5개 부스 확인
    @GET("api/booths")
    suspend fun getPopularBooths(
        @Query("festivalId") festivalId: Long,
    ): PopularBoothsResponse

    // 해당 축제 부스 전체 조회
    @GET("api/booths/{festival-id}/booths")
    suspend fun getAllBooths(
        @Path("festival-id") festivalId: Long,
    ): AllBoothsResponse

    // 특정 부스 조회
    @GET("api/booths/{booth-id}")
    suspend fun getBoothDetail(
        @Path("booth-id") boothId: Long,
    ): BoothDetailResponse

    // 좋아요 생성
    @POST("api/likes")
    suspend fun likeBooth(
        @Body likeBoothRequest: LikeBoothRequest,
    ): LikeBoothResponse

    // 특정 사용자가 좋아요한 부스 목록 조회
    @GET("api/likes")
    suspend fun getLikedBooths(
        @Query("token") token: String,
    ): LikedBoothsResponse

    // 특정 부스의 좋아요 개수 조회
    @GET("api/likes/{booth-id}")
    suspend fun getBoothLikes(
        @Path("booth-id") boothId: Long,
    ): LikeBoothResponse

    // Pin 번호 받기
    @POST("waiting/pin/check")
    suspend fun checkPinValidation(
        @Body checkPinValidationRequest: CheckPinValidationRequest,
    ): CheckPinValidationResponse

    // 웨이팅 추가
    @POST("waiting")
    suspend fun requestBoothWaiting(
        @Body boothWaitingRequest: BoothWaitingRequest,
    ): WaitingResponse

    // 내 예약된 웨이팅 조회(deviceID 기준)
    @GET("waiting/me/{deviceId}")
    suspend fun getMyWaitingList(
        @Path("deviceId") deviceId: String,
    ): MyWaitingResponse

    // 사용자 측의 웨이팅 취소
    @PUT("waiting")
    suspend fun cancelBoothWaiting(
        @Body request: WaitingRequest,
    ): WaitingResponse

    // 관심 축제 등록
    @POST("megaphone/subscribe")
    suspend fun registerLikedFestival(
        @Body likedFestivalRequest: LikedFestivalRequest,
    )

    // 관심 축제 해제
    @HTTP(method = "DELETE", path = "megaphone/subscribe", hasBody = true)
    suspend fun unregisterLikedFestival(
        @Body likedFestivalRequest: LikedFestivalRequest,
    )

    // 스탬프 조회
    @GET("stamps")
    suspend fun getCollectedStampCount(
        @Query("token") token: String,
    ): CollectedStampCountResponse

    // 스탬프 추가
    @POST("stamps")
    suspend fun registerStamp(
        @Body registerStampRequest: RegisterStampRequest,
    )

    // 스탬프 기능 활성화된 부스 확인
    @GET("stamps/{festival-id}")
    suspend fun getStampEnabledBoothList(
        @Path("festival-id") festivalId: Long,
    ): StampBoothsResponse
}
