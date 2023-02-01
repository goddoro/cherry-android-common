package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.PointRequestType
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreatePointRequest(
    @SerializedName("agentName")
    val agentName: String,
    @SerializedName("point")
    val point: Int,
    @SerializedName("money")
    val money: Int,
    @SerializedName("type")
    val type: PointRequestType,
): Parcelable
