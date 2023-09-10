package doro.android.data.service

import com.google.gson.annotations.SerializedName
import doro.android.data.dto.AgentResponse
import doro.android.data.dto.EmptyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AgentService {
    @GET("/agents/search")
    suspend fun searchOne(@Query("name") name: String): AgentResponse

    @GET("/agents/{id}")
    suspend fun findOne(@Path("id") id: Int): AgentResponse

    @POST("/auth/sign-up-agent")
    suspend fun signUp(@Body request: AgentSignUpRequest): EmptyResponse

    @PATCH("/agents/{id}")
    suspend fun updateToken(@Body request: TokenUpdateRequest): EmptyResponse
}

data class AgentSignUpRequest(
    @SerializedName("name")
    val nickName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("telephone")
    val telephone: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("email")
    val email: String
)

data class TokenUpdateRequest(
    @SerializedName("agentId")
    val agentId: Int,
    @SerializedName("token")
    val token: String
)