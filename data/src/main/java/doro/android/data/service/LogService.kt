package doro.android.data.service

import doro.android.data.dto.CherryLogEventRequest
import doro.android.data.dto.CherryStreamingLogEventRequest
import doro.android.data.dto.LogEventRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LogService {
    @POST("/logger")
    suspend fun sendEvent(@Body log: CherryLogEventRequest)

    @POST("/logger")
    suspend fun sendStreamingEvent(@Body log: CherryStreamingLogEventRequest)
}