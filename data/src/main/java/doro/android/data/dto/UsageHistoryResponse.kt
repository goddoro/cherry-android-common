package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.UsageHistory
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsageHistoryResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("machine")
    val machine: MachineResponse,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("credit")
    val credit: Int,
) : Parcelable {
    fun toDomain(): UsageHistory {
        return UsageHistory(
            id = id,
            machine = machine.toDomain(),
            createdAt = createdAt,
            endTime = endTime,
            credit = credit,
        )
    }
}
