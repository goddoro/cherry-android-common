package doro.android.domain.entity

data class Machine(
    val id: Int,
    val status: MachineStatus?,
    val number: String,
    val game: Game? = null,
    val occupiedUserId: Int?,
)

enum class MachineStatus {
    BROKEN, READY, HOLDING, PLAYING, PENDING, EVENT, SPECIAL_GAME_AUTO;
    fun isAvailable() = this == READY
}
