package doro.android.domain.entity



data class UserPointRequest(
    val id: Int,
    val agent: Agent,
    val point: Int,
    val status: PointRequestStatus,
    val user: User,
    val createdAt: String,
    val updatedAt: String,
    val money: Int,
    val type: PointRequestType
)

enum class PointRequestStatus {
    REQUESTED, DONE, CANCELLED, ORDER, PAY, DEMAND
}

enum class PointRequestType {
    POINT, MONEY
}
