package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Game
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("buttons")
    val buttons: List<GameButtonResponse>? = null
) : Parcelable {

    fun toDomain() = Game(
        id = id,
        title = title,
        description = description,
        thumbnail = thumbnail,
        buttons = buttons?.map { it.toDomain() },
    )
}