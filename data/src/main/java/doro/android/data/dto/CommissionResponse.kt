package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Agent
import doro.android.domain.entity.Commission
import doro.android.domain.entity.CommissionStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommissionResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("commission")
    val commission: Int,

    @SerializedName("status")
    val status: CommissionStatus,

    @SerializedName("agent")
    val agent: AgentResponse?,

    @SerializedName("credit")
    val credit: Int,
) : Parcelable {
    fun toDomain(): Commission {
        return Commission(
            id = this.id,
            commission = this.commission,
            status = this.status,
            agent = this.agent?.toDomain(),
            credit = this.credit,
        )
    }
}

