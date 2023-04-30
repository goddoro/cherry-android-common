package doro.android.domain.entity

data class MoneyHistory(
    val id: Int,
    val credit: Int,
    val reason: MoneyHistoryReason,
)

enum class MoneyHistoryReason {
    S, I, O, R
}
