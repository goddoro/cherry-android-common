package doro.android.data.service

import doro.android.data.dto.UsageHistoryListResponse
import retrofit2.http.GET

interface UsageHistoryService {
    @GET("/usage-histories")
    suspend fun fetch(): UsageHistoryListResponse
}