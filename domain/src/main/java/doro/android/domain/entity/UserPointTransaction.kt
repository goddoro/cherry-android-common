package doro.android.domain.entity

data class UserPointTransaction(
    val id: Int,
    val game: Game? = null,
    val createdAt: String,
    val agent: Agent? = null,
    val before: Int,
    val after: Int,
    val reason: PointTransactionReason
)

enum class PointTransactionReason {
    POINT_CARRY_BY_CASHER, POINT_CREDIT_IN, POINT_CREDIT_OUT, POINT_RELEASE
}
