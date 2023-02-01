package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.UserPointRequest
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointRequestsResponse(
    @SerializedName("userPointRequests")
    val userPointRequests: List<UserPointRequestResponse>
) : Parcelable