package doro.android.data.service

import doro.android.data.dto.VersionResponse
import retrofit2.http.GET

interface AppService {
    @GET("/")
    suspend fun getVersion(): VersionResponse
}