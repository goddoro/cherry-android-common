package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.PointTransactionReason
import doro.android.domain.entity.UserPointTransaction
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPointTransactionResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("game")
    val game: GameResponse? = null,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("agent")
    val agent: AgentResponse? = null,

    @SerializedName("before")
    val before: Int,

    @SerializedName("after")
    val after: Int,

    @SerializedName("reason")
    val reason: PointTransactionReason
) : Parcelable {

    fun toDomain() = UserPointTransaction(
        id = id,
        game = game?.toDomain(),
        createdAt = createdAt,
        agent = agent?.toDomain(),
        before = before,
        after = after,
        reason = reason,

    )
}
