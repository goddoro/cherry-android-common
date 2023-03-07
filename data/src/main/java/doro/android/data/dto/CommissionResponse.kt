package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Agent
import doro.android.domain.entity.Commission
import doro.android.domain.entity.CommissionStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommissionResponse(
    @SerializedName("commission")
    val commission: Int,

    @SerializedName("status")
    val status: CommissionStatus,

    @SerializedName("agent")
    val agent: AgentResponse?
) : Parcelable {
    fun toDomain(): Commission {
        return Commission(
            commission = this.commission,
            status = this.status,
            agent = this.agent?.toDomain(),
        )
    }
}

