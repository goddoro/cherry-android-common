package doro.android.domain.repository

import doro.android.domain.enums.CherryButtonEvent
import doro.android.domain.enums.CherryUI

interface LogRepository {
    suspend fun sendClickEvent(where: CherryUI, name: CherryButtonEvent)
    suspend fun sendPingEvent(
        machineNumber: String,
        credit: Int,
        point: Int,
        gameName: String,
    )
    suspend fun sendVisitEvent(where: CherryUI)
    suspend fun sendBugEvent(message: String, type: String)
    suspend fun sendGameButtonEvent(
        name: String,
        credit: Int? = null,
        cameraMode: String? = null,
        machineNumber: String? = null,
    )
}