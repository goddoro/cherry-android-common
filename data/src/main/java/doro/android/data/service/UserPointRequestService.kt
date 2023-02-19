package doro.android.data.service

import doro.android.data.dto.*
import retrofit2.http.*

interface UserPointRequestService {

    @GET("/user-point-request")
    suspend fun fetchList(@QueryMap query: FindUserPointRequest): PointRequestsResponse

    @PATCH("/user-point-request/carry")
    suspend fun carry(@Body body: UserPointRequestCarryRequest): EmptyResponse

    @POST("/user-point-request")
    suspend fun create(@Body body: CreatePointRequest): EmptyResponse

    @GET("/user-point-request/{id}")
    suspend fun findOne(@Path("id") id: Int): UserPointRequestResponse

}