package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.PointRequestType
import kotlinx.parcelize.Parcelize

@Parcelize
data class FindUserPointRequest(
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("agentId")
    val agentId: Int? = null,
): Parcelable
