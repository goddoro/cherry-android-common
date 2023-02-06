package doro.android.data.repository

import android.util.Log
import doro.android.core.util.UserHolder
import doro.android.data.dto.*
import doro.android.data.service.LogService
import doro.android.domain.enums.CherryAction
import doro.android.domain.enums.CherryActionData
import doro.android.domain.enums.CherryButtonEvent
import doro.android.domain.enums.CherryUI
import doro.android.domain.repository.LogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogRepositoryImpl @Inject constructor(
    private val logService: LogService,
    private val userHolder: UserHolder,
) : LogRepository {
    override suspend fun sendClickEvent(where: CherryUI, name: CherryButtonEvent) =
        withContext(Dispatchers.IO) {
            val request = LogEventRequest(
                playerId = userHolder.getUserId(),
                action = CherryAction.clicked,
                where = where,
                data = ButtonClickData(name = name),
            )
            logService.sendEvent(CherryLogEventRequest(request))
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
            where = CherryUI.game,
            data = GamePingData(
                machineNumber = machineNumber,
                credit = credit,
                point = point,
                gameName = gameName,
            ),
        )
        logService.sendEvent(CherryLogEventRequest(request))
    }

    override suspend fun sendVisitEvent(where: CherryUI) = withContext(Dispatchers.IO) {
        val request = LogEventRequest(
            playerId = userHolder.getUserId(),
            action = CherryAction.visited,
            where = where,
        )
        logService.sendEvent(CherryLogEventRequest(request))
    }

    override suspend fun sendStreamingBugEvent(message: String) = withContext(Dispatchers.IO) {
        val request = CherryStreamingLogEvent(
            playerId = userHolder.getUserId(),
            message = message,
        )
        logService.sendStreamingEvent(CherryStreamingLogEventRequest(request))
    }
}