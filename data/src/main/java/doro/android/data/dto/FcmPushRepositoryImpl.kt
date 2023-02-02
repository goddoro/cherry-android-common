package doro.android.data.dto

import doro.android.data.service.FcmPushService
import doro.android.domain.repository.FcmPushRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FcmPushRepositoryImpl @Inject constructor(
    private val fcmPushService: FcmPushService,
): FcmPushRepository {
    override suspend fun sendJackPotEvent(gameName: String, jackPotCredit: Int) = withContext(Dispatchers.IO) {
        val request = JackPotRequest(
            gameName = gameName,
            jackPotCredit = jackPotCredit,
        )
        fcmPushService.sendJackPot(request)
    }
}