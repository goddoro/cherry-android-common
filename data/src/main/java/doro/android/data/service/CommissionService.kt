package doro.android.data.service

import doro.android.data.dto.CommissionApproveRequest
import doro.android.data.dto.CommissionListResponse
import doro.android.data.dto.EmptyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Query

interface CommissionService {
    @GET("/commission")
    suspend fun fetch(@Query("agentId") agentId: Int?): CommissionListResponse

    @PUT("/commission")
    suspend fun approve(@Body body: CommissionApproveRequest): EmptyResponse

}