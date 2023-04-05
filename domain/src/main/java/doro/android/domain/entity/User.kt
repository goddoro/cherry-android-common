package doro.android.domain.entity

data class User(
    val id: Int,
    val email: String,
    val username: String,
    val point: Int,
    val token: String,
    val agent: Agent?,
)