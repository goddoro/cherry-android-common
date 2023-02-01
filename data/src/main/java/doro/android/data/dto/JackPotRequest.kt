package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JackPotRequest(
    @SerializedName("jackPotCredit")
    val jackPotCredit: Int,
    @SerializedName("gameName")
    val gameName: String,
) : Parcelable