package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoneyHistoryListResponse(
    @SerializedName("moneyHistories")
    val moneyHistories: List<MoneyHistoryResponse>
) : Parcelable
