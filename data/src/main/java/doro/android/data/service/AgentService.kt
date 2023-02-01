package doro.android.data.service

import doro.android.data.dto.AgentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AgentService {
    @GET("/agents/search")
    suspend fun findOne(@Query("name") name: String): AgentResponse
}