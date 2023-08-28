package doro.android.data.service

import doro.android.data.dto.UsageHistoryListResponse
import doro.android.data.dto.UsageHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsageHistoryService {
    @GET("/usage-histories")
    suspend fun fetch(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String,
        @Query("userId") userId: Int
    ): UsageHistoryListResponse

    @GET("/usage-histories/{id}")
    suspend fun findOne(@Path("id") id: Int): UsageHistoryResponse
}