package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VersionResponse(
    @SerializedName("version")
    val version: String
): Parcelable
