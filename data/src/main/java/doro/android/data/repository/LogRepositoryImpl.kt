package doro.android.data.repository

import android.util.Log
import doro.android.core.util.UserHolder
import doro.android.data.dto.*
import doro.android.data.service.LogService
import doro.android.domain.enums.*
import doro.android.domain.repository.LogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogRepositoryImpl @Inject constructor(
    private val logService: LogService,
    private val userHolder: UserHolder,
) : LogRepository {
    private val TAG = LogRepository::class.java.simpleName
    override suspend fun sendClickEvent(where: CherryUI, name: CherryButtonEvent): Unit =
        withContext(Dispatchers.IO) {
            try {
                val request = LogEventRequest(
                    playerId = userHolder.getUserId(),
                    action = CherryAction.clicked,
                    where = where,
                    data = ButtonClickData(name = name),
                )
                logService.sendEvent(CherryLogEventRequest(request))
            } catch (e: Throwable){
                Log.d(TAG, e.message.orEmpty())
            }
        }

    override suspend fun sendPingEvent(
        machineNumber: String,
        credit: Int,
        point: Int,
        gameName: String,
    ): Unit = withContext(Dispatchers.IO) {
        try {
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
        } catch (e: Throwable){
            Log.d(TAG, e.message.orEmpty())
        }
    }

    override suspend fun sendVisitEvent(where: CherryUI): Unit = withContext(Dispatchers.IO) {
        try {
            val request = LogEventRequest(
                playerId = userHolder.getUserId(),
                action = CherryAction.visited,
                where = where,
            )
            logService.sendEvent(CherryLogEventRequest(request))
        } catch (e: Throwable){
            Log.d(TAG, e.message.orEmpty())
        }
    }

    override suspend fun sendStreamingBugEvent(message: String): Unit = withContext(Dispatchers.IO) {
        try {
            val request = CherryStreamingLogEvent(
                playerId = userHolder.getUserId(),
                message = message,
            )
            logService.sendStreamingEvent(CherryStreamingLogEventRequest(request))
        } catch (e: Throwable) {
            Log.d(TAG, e.message.orEmpty())
        }
    }

    override suspend fun sendGameButtonEvent(
        name: String,
        credit: Int?,
        cameraMode: CherryCameraMode?,
        machineNumber: String?,
    ): Unit = withContext(Dispatchers.IO) {
        try {
            val request = LogEventRequest(
                playerId = userHolder.getUserId(),
                action = CherryAction.clicked,
                where = CherryUI.game,
                data = GameButtonClickData(
                    name = name,
                    credit = credit,
                    cameraMode = cameraMode,
                    machineNumber = machineNumber,
                )
            )
            logService.sendEvent(CherryLogEventRequest(request))
        } catch (e: Throwable) {
            Log.d(TAG, e.message.orEmpty())
        }
    }
}