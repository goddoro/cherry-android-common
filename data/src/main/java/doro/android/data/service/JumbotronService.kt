package doro.android.data.service

import doro.android.data.dto.JumbotronListResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface JumbotronService {

    @POST("/jumbotrons")
    suspend fun create()

    @GET("/jumbotrons")
    suspend fun fetchList(): JumbotronListResponse
}