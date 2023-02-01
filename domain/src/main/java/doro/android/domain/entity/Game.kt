package doro.android.domain.entity

data class Game(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String,
    val buttons: List<GameButton>? = null,
)