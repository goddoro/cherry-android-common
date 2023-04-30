package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.MoneyHistory
import doro.android.domain.entity.MoneyHistoryReason
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoneyHistoryResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("credit")
    val credit: Int,

    @SerializedName("reason")
    val reason: MoneyHistoryReason,
) : Parcelable {

    fun toDomain(): MoneyHistory {
        return MoneyHistory(
            id = id,
            credit = credit,
            reason = reason,
        )
    }
}
