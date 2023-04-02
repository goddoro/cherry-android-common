package doro.android.domain.entity

data class Machine(
    val id: Int,
    val status: MachineStatus?,
    val number: String,
    val game: Game? = null,
    val occupiedUserId: Int?,
    val credit: Int? = null,
)

enum class MachineStatus {
    BROKEN, READY, HOLDING, PLAYING, PENDING, EVENT, SPECIAL_GAME_AUTO, WAIT_TO_HOLDING;
    fun isAvailable() = this == READY
}
