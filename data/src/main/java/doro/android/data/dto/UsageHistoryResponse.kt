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
    @SerializedName("machine")
    val machine: MachineResponse,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("before")
    val before: Int,
    @SerializedName("after")
    val after: Int,

) : Parcelable {
    fun toDomain(): UsageHistory {
        return UsageHistory(
            id = id,
            machine = machine.toDomain(),
            createdAt = startTime,
            endTime = endTime,
            before = before,
            after = after,
        )
    }
}
