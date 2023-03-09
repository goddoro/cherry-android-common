package doro.android.domain.entity

data class UsageHistory(
    val id: Int,
    val machine: Machine,
    val createdAt: String,
    val endTime: String?,
    val before: Int,
    val after: Int
)
