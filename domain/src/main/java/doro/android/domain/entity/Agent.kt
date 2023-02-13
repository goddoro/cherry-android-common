package doro.android.domain.entity

data class Agent(
    val id: Int,
    val name: String,
    val email: String,
    val clients: List<User>? = null,
)
