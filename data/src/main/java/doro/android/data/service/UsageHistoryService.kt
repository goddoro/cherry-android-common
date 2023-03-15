package doro.android.data.service

import doro.android.data.dto.UsageHistoryListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsageHistoryService {
    @GET("/usage-histories")
    suspend fun fetch(@Query("startDate") startDate: String, @Query("endDate") endDate: String): UsageHistoryListResponse
}