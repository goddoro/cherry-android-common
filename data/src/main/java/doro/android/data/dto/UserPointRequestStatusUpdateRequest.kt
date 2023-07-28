package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPointRequestStatusUpdateRequest(
    @SerializedName("pointRequestId")
    val pointRequestId: Int,

    @SerializedName("status")
    val status: String
) : Parcelable
