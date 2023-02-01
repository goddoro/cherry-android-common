package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MachineListResponse(
    @SerializedName("machines")
    val machines: List<MachineResponse>
) : Parcelable