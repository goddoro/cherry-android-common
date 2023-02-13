package doro.android.domain.repository

import doro.android.domain.enums.CherryButtonEvent
import doro.android.domain.enums.CherryCameraMode
import doro.android.domain.enums.CherryGameButtonName
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
    suspend fun sendStreamingBugEvent(message: String)
    suspend fun sendGameButtonEvent(
        name: CherryGameButtonName,
        credit: Int? = null,
        cameraMode: CherryCameraMode? = null,
        machineNumber: String? = null,
    )
}