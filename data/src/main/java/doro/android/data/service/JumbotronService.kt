package doro.android.data.service

import doro.android.data.dto.JumbotronCreateRequest
import doro.android.data.dto.JumbotronListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JumbotronService {

    @POST("/jumbotrons")
    suspend fun create(@Body body : JumbotronCreateRequest)

    @GET("/jumbotrons")
    suspend fun fetchList(): JumbotronListResponse
}