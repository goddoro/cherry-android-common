package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.AgentPointRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPointRequestListResponse(
    @SerializedName("userPointRequests")
    val userPointRequests: UserPointList? = null,
    @SerializedName("remainRequests")
    val remainRequests: RemainRequests
): Parcelable {
    fun toDomain(): AgentPointRequest {
        return AgentPointRequest(
            moneyCount = remainRequests.money,
            pointCount = remainRequests.point,
            moneyRequests = userPointRequests?.moneyRequests?.map { it.toDomain()} ?: listOf(),
            pointRequests = userPointRequests?.pointRequests?.map { it.toDomain()} ?: listOf()
        )
    }
}

@Parcelize
data class UserPointList(
    @SerializedName("point")
    val pointRequests: List<UserPointRequestResponse>,
    @SerializedName("money")
    val moneyRequests: List<UserPointRequestResponse>
): Parcelable

@Parcelize
data class RemainRequests(
    @SerializedName("point")
    val point: Int,
    @SerializedName("money")
    val money: Int
): Parcelable
