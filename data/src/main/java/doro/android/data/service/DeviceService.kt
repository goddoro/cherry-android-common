package doro.android.data.service

import doro.android.data.dto.EmptyResponse
import retrofit2.http.DELETE
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DeviceService {

    @POST("/devices")
    @FormUrlEncoded()
    suspend fun register(@FieldMap params: HashMap<String,String>) : EmptyResponse

    @DELETE("/devices")
    suspend fun unregister() : EmptyResponse
}