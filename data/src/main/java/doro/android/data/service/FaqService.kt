package doro.android.data.service

import doro.android.data.dto.FaqListResponse
import retrofit2.http.GET

interface FaqService {

    @GET("/faq")
    suspend fun getFaqList(): FaqListResponse
}