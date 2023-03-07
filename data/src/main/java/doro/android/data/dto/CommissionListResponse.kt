package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommissionListResponse(
    @SerializedName("commissions")
    val commissions: List<CommissionResponse>
): Parcelable
