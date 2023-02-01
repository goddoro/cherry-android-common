package doro.android.data.service

import doro.android.data.dto.GameListResponse
import retrofit2.http.GET

interface GameService  {

    @GET("/games")
    suspend fun findList() : GameListResponse
}