package doro.android.domain.entity

data class AgentPointRequest(
    val moneyCount: Int,
    val pointCount: Int,
    val moneyRequests: List<UserPointRequest?>,
    val pointRequests: List<UserPointRequest?>
)
