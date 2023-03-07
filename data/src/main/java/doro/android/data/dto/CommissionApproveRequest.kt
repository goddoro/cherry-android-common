package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.CommissionStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommissionApproveRequest(
    @SerializedName("status")
    val status: CommissionStatus,

    @SerializedName("ids")
    val ids: List<Int>
): Parcelable
