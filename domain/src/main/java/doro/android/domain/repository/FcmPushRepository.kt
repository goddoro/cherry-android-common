package doro.android.domain.repository

interface FcmPushRepository {
    suspend fun sendJackPotEvent(gameName: String, jackPotCredit: Int)
}