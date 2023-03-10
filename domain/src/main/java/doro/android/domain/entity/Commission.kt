package doro.android.domain.entity

data class Commission(
    val id: Int,
    val credit: Int,
    val commission: Int,
    val agent: Agent?,
    val status: CommissionStatus
)

enum class CommissionStatus {
    PENDING, APPROVED, COMPLETED, REJECTED
}