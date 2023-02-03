package doro.android.data.repository

import doro.android.core.util.UserHolder
import doro.android.data.dto.GamePingData
import doro.android.data.dto.LogEventRequest
import doro.android.data.service.LogService
import doro.android.domain.enums.CherryAction
import doro.android.domain.enums.CherryActionData
import doro.android.domain.enums.CherryUI
import doro.android.domain.repository.LogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogRepositoryImpl @Inject constructor(
    private val logService: LogService,
    private val userHolder: UserHolder,
) : LogRepository {
    override suspend fun sendClickEvent(where: CherryUI, data: CherryActionData?) =
        withContext(Dispatchers.IO) {
            val request = LogEventRequest(
                playerId = userHolder.getUserId(),
                action = CherryAction.clicked,
                ui = where,
                data = data,
            )
            logService.sendEvent(request)
        }

    override suspend fun sendPingEvent(
        machineNumber: String,
        credit: Int,
        point: Int,
        gameName: String,
    ) = withContext(Dispatchers.IO) {
        val request = LogEventRequest(
            playerId = userHolder.getUserId(),
            action = CherryAction.ping,
            ui = CherryUI.game,
            data = GamePingData(
                machineNumber = machineNumber,
                credit = credit,
                point = point,
                gameName = gameName,
            ),
        )
        logService.sendEvent(request)
    }

    override suspend fun sendEnterEvent(where: CherryUI) = withContext(Dispatchers.IO) {
        val request = LogEventRequest(
            playerId = userHolder.getUserId(),
            action = CherryAction.enter,
            ui = where,
        )
        logService.sendEvent(request)
    }
}