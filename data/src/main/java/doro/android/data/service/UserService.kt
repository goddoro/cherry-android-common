package doro.android.data.service

import doro.android.data.dto.*
import retrofit2.http.*

interface UserService {
    @GET("/users/{id}")
    suspend fun findOne(@Path("id") userId: Int): UserResponse

    @GET("/users/search")
    suspend fun search(@Query("query") query: String): UserSearchResponse?

    @PATCH("users/{id}")
    @FormUrlEncoded
    suspend fun update(
        @Path("id") userId: Int,
        @FieldMap params: HashMap<String, Any>
    ): UserResponse

    @PATCH("/users/{id}/point")
    suspend fun updatePoint(
        @Path("id") userId: Int,
        @Body body: UserPointUpdateRequest
    ): UserResponse

    @GET("/users")
    suspend fun fetchAll(): UserListResponse

    @DELETE("/users")
    suspend fun signOut(): EmptyResponse

    @PATCH("/users/{id}/agent")
    suspend fun updateAgent(@Path("id") userId: Int, @Body updateAgentRequest: UpdateAgentRequest): UserResponse

}