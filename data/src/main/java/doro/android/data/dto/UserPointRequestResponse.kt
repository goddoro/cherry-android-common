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
    @SerializedName("point")
    val point: Int,
    @SerializedName("status")
    val status: PointRequestStatus,
    @SerializedName("createdAt")
    val createdAt: String,
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
        createdAt = createdAt,
        money = money,
        type = type,
    )
}
