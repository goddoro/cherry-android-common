package doro.android.domain.repository

import doro.android.domain.enums.CherryActionData
import doro.android.domain.enums.CherryUI

interface LogRepository {
    suspend fun sendClickEvent(where: CherryUI, data: CherryActionData? = null)
    suspend fun sendPingEvent(
        machineNumber: String,
        credit: Int,
        point: Int,
        gameName: String,
    )
    suspend fun sendEnterEvent(where: CherryUI)
}