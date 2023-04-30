package doro.android.domain.entity

data class UsageHistory(
    val id: Int,
    val machineNumber: String,
    val createdAt: String,
    val endTime: String?,
    val before: Int,
    val after: Int,
    val game: Game,
    val moneyHistory: List<MoneyHistory>?,
)
