package doro.android.domain.entity

data class GameButton(
    val id: Int,
    val name: String,
    val number: Int,
    val type: ButtonType,
)

enum class ButtonType {
    SIMPLE,
    LINE,
    START,
    AUTO_START,
    TOP,
    HELP
}

