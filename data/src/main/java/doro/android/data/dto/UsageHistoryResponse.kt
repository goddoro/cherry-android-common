package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.UsageHistory
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsageHistoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("machineNumber")
    val machineNumber: String,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("before")
    val before: Int,
    @SerializedName("after")
    val after: Int,
    @SerializedName("game")
    val game: GameResponse?,
    @SerializedName("moneyHistories")
    val moneyHistories: List<MoneyHistoryResponse>?,
    @SerializedName("user")
    val user: UserResponse?,

) : Parcelable {
    fun toDomain(): UsageHistory {
        return UsageHistory(
            id = id,
            machineNumber = machineNumber,
            createdAt = startTime,
            endTime = endTime,
            before = before,
            after = after,
            game = game?.toDomain(),
            moneyHistories = moneyHistories?.map { it.toDomain() },
            user = user?.toDomain()
        )
    }
}
