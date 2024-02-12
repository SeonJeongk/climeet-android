package com.climus.climeet.data.remote

import com.climus.climeet.data.model.request.CreateTimerClimbingRecordRequest
import com.climus.climeet.data.model.request.GetGymRouteInfoRequest
import com.climus.climeet.data.model.request.ShortsDetailRequest
import com.climus.climeet.data.model.response.BannerDetailInfoResponse
import com.climus.climeet.data.model.response.BestClearClimberSimpleResponse
import com.climus.climeet.data.model.response.BestFollowGymSimpleResponse
import com.climus.climeet.data.model.response.BestLevelCimberSimpleResponse
import com.climus.climeet.data.model.response.BestRecordGymDetailInfoResponse
import com.climus.climeet.data.model.response.BestRouteDetailInfoResponse
import com.climus.climeet.data.model.response.BestTimeClimberSimpleResponse
import com.climus.climeet.data.model.response.GetGymFilteringKeyResponse
import com.climus.climeet.data.model.response.GetGymProfileResponse
import com.climus.climeet.data.model.response.GetGymRouteInfoResponse
import com.climus.climeet.data.model.response.GetSelectDateRecordResponse
import com.climus.climeet.data.model.response.RefreshTokenResponse
import com.climus.climeet.data.model.response.SearchAvailableGymResponse
import com.climus.climeet.data.model.response.SearchGymResponse
import com.climus.climeet.data.model.response.ShortsListResponse
import com.climus.climeet.data.model.response.ShortsUpdatedFollowResponse
import com.climus.climeet.data.model.response.UploadImgResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {

    @Multipart
    @POST("api/file")
    suspend fun uploadFile(@Part file: MultipartBody.Part): Response<UploadImgResponse>

    @GET("api/gyms/search/all")
    suspend fun searchGym(
        @Query("gymname") gymName: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchGymResponse>

    @GET("api/gyms/search")
    suspend fun searchAvailableGym(
        @Query("gymname") gymName: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchAvailableGymResponse>

    @POST("refresh-token")
    suspend fun refreshToken(
        @Query("refreshToken") refreshToken: String
    ): Response<RefreshTokenResponse>

    @GET("api/shorts/latest")
    suspend fun getRecentShorts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ShortsListResponse>

    @GET("api/shorts/popular")
    suspend fun getPopularShorts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ShortsListResponse>

    @GET("api/shorts/profile")
    suspend fun getShortsUpdatedFollow(): Response<List<ShortsUpdatedFollowResponse>>

    @GET("/api/climbing-records/between-dates")
    suspend fun getSelectDateRecord(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<List<GetSelectDateRecordResponse>>

    @GET("/api/banners")
    suspend fun findBannerListBetweenDates(): Response<List<BannerDetailInfoResponse>>

    @GET("/api/home/rank/weeks/climbers/clear")
    suspend fun findClimberRankingOrderClearCount(): Response<List<BestClearClimberSimpleResponse>>

    @GET("/api/home/rank/weeks/climbers/time")
    suspend fun findClimberRankingOrderTime(): Response<List<BestTimeClimberSimpleResponse>>

    @GET("/api/home/rank/weeks/climbers/level")
    suspend fun findClimberRankingOrderLevel(): Response<List<BestLevelCimberSimpleResponse>>


    @GET("/api/home/rank/weeks/gyms/follow")
    suspend fun findGymRankingOrderFollowCount(
    ): Response<List<BestFollowGymSimpleResponse>>

    @GET("/api/home/rank/weeks/gyms/record")
    suspend fun findGymRankingListOrderSelectionCount(
    ): Response<List<BestRecordGymDetailInfoResponse>>

    @GET("/api/home/rank/weeks/routes")
    suspend fun findRouteRankingOrderSelectionCount(
    ): Response<List<BestRouteDetailInfoResponse>>


    @GET("/api/gyms/{gymId}/version/key")
    suspend fun getGymFilteringKey(
        @Path("gymId") gymId: Long,
    ): Response<GetGymFilteringKeyResponse>

    @POST("/api/gyms/{gymId}/version/route")
    suspend fun getGymRouteInfoList(
        @Path("gymId") gymId: Long,
        @Body params: GetGymRouteInfoRequest
    ): Response<GetGymRouteInfoResponse>

    @POST("/api/climbing-records")
    suspend fun createTimerClimbingRecord(
        @Body params: CreateTimerClimbingRecordRequest
    ): Response<String>

    @GET("/api/gyms/{gymId}")
    suspend fun getGymProfile(
        @Path("gymId") gymId: Long
    ): Response<GetGymProfileResponse>

    @Multipart
    @POST("/api/shorts")
    suspend fun uploadShorts(
        @Part video: MultipartBody.Part?,
        @Part("createShortsRequest") createShortsRequest: ShortsDetailRequest
    ): Response<Unit>

    @PATCH("/api/shorts/{shortsId}/bookmarks")
    suspend fun patchBookMarks(
        @Path("shortsId") shortsId: Long
    ): Response<Unit>

    @PATCH("/api/Shorts/{shortsId}/likes")
    suspend fun patchFavorites(
        @Path("shortsId") shortsId: Long
    ): Response<Unit>

}