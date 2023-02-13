package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JumbotronListResponse(
    @SerializedName("jumbotrons")
    val jumbotrons: List<JumbotronResponse>
): Parcelable
