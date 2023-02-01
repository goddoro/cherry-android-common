package doro.android.data.service

import doro.android.data.dto.JackPotRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmPushService {

    @POST("push-fcm/jack-pot")
    suspend fun sendJackPot(@Body jackPotRequest: JackPotRequest)
}