package doro.android.data.service

import doro.android.data.dto.UserListResponse
import doro.android.data.dto.UserResponse
import doro.android.data.dto.UserSearchResponse
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

    @GET("/users")
    suspend fun fetchAll(): UserListResponse

}