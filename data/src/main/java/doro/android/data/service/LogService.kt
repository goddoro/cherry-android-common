package doro.android.data.service

import doro.android.data.dto.CherryAndroidBugEvent
import retrofit2.http.Body
import retrofit2.http.POST

interface LogService {
    @POST("/logger")
    suspend fun sendStreamingEvent(@Body log: CherryAndroidBugEvent)
}
