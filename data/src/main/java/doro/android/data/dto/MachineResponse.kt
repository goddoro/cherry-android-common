package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Machine
import doro.android.domain.entity.MachineStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class MachineResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("status")
    val status: MachineStatus?,

    @SerializedName("number")
    val number: String,

    @SerializedName("game")
    val game: GameResponse? = null,

    @SerializedName("occupiedUserId")
    val occupiedUserId: Int?,

    @SerializedName("credit")
    val credit: Int?,
) : Parcelable {
    fun toDomain() = Machine(
        id = id,
        status = status,
        number = number,
        game = game?.toDomain(),
        occupiedUserId = occupiedUserId,
        credit = credit,
    )
}
