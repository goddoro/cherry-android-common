package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.ButtonType
import doro.android.domain.entity.GameButton
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameButtonResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int,
    @SerializedName("type")
    val type: ButtonType
) : Parcelable {
    fun toDomain() = GameButton(
        id = id,
        name = name,
        number = number,
        type = type,
    )
}