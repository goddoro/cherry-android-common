package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmptyResponse(
    @SerializedName("success")
    val success: Boolean
) : Parcelable
