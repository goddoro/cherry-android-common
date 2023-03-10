package doro.android.data.service

import doro.android.data.dto.CreatePointRequest
import doro.android.data.dto.EmptyResponse
import doro.android.data.dto.PointRequestsResponse
import doro.android.data.dto.PointTransactionsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PointTransactionService {
    @GET("/user-point-transactions/")
    suspend fun fetch(): PointTransactionsResponse
}