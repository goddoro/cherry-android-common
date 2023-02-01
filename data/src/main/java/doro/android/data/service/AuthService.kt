package doro.android.data.service

import doro.android.data.dto.EmptyResponse
import doro.android.data.dto.SignInResponse
import doro.android.data.dto.SignUpResponse
import retrofit2.http.*

interface AuthService {

    @POST("auth/sign-in")
    @FormUrlEncoded
    suspend fun signIn(@FieldMap params: Map<String, String>): SignInResponse

    @POST("auth/sign-up")
    @FormUrlEncoded
    suspend fun signUp(@FieldMap params: Map<String, String>): SignUpResponse

    @POST("auth/password-reset")
    @FormUrlEncoded
    suspend fun sendVerifyCodeEmail(@FieldMap params: Map<String,String>): EmptyResponse

    @POST("auth/verify-code")
    @FormUrlEncoded
    suspend fun verifyCode(@FieldMap params: Map<String,String>): EmptyResponse

}