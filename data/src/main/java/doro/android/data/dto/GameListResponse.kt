package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Game
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameListResponse(
    @SerializedName("games")
    val games: List<GameResponse>
): Parcelable
