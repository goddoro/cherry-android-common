package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.PointRequestStatus
import doro.android.domain.entity.PointRequestType
import doro.android.domain.entity.UserPointRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPointRequestResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("agent")
    val agent: AgentResponse,
    @SerializedName("user")
    val user: UserResponse,
    @SerializedName("point")
    val point: Int,
    @SerializedName("status")
    val status: PointRequestStatus,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("money")
    val money: Int,
    @SerializedName("type")
    val type: PointRequestType
): Parcelable {
    fun toDomain() = UserPointRequest(
        id = id,
        agent = agent.toDomain(),
        point = point,
        status = status,
        createdAt = createdAt.orEmpty(),
        updatedAt = updatedAt.orEmpty(),
        money = money,
        type = type,
        user = user.toDomain()
    )
}
