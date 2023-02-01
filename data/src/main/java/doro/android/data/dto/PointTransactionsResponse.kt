package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.UserPointTransaction
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointTransactionsResponse(
    @SerializedName("userPointTransactions")
    val userPointTransactions: List<UserPointTransactionResponse>
): Parcelable
