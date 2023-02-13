package doro.android.data.service

import doro.android.data.dto.AgentResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AgentService {
    @GET("/agents/search")
    suspend fun searchOne(@Query("name") name: String): AgentResponse

    @GET("/agents/{id}")
    suspend fun findOne(@Path("id") id: Int): AgentResponse
}