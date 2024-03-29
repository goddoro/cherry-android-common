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
    BROKEN, READY, HOLDING, PLAYING, PENDING, EVENT, SPECIAL_GAME_AUTO, WAIT_TO_HOLDING, UNDER_MAINTENANCE;
    fun isAvailable() = this == READY
    fun isInGame() = this == HOLDING || this == PLAYING || this == PENDING || this == EVENT || this == SPECIAL_GAME_AUTO
}
