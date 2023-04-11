package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.User
import doro.android.domain.entity.UserRole
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("point")
    val point: Int,

    @SerializedName("agent")
    val agent: AgentResponse?,

    @SerializedName("role")
    val role: UserRole?,
) : Parcelable {

    fun toDomain(token: String? = null): User {
        return User(
            id = id,
            email = email,
            username = username,
            point = point,
            token = token.orEmpty(),
            agent = agent?.toDomain(),
            role = role ?: UserRole.player,
        )
    }
}
