package doro.android.domain.repository

import doro.android.domain.entity.GameButton
import doro.android.domain.entity.Machine

interface MachineRepository {
    suspend fun fetchList(): List<Machine>
    suspend fun creditIn(machineNumber: String, point: Int)
    suspend fun creditOut(machineNumber: String, point: Int)
    suspend fun holdSlot(machineNumber: String)
    suspend fun releaseSlot(machineNumber: String)
    suspend fun pressButton(machineNumber: String, button: GameButton)
    suspend fun selectEvent(machineNumber: String, eventNumber: Int)
    suspend fun getAddress(machineNumber: String)
    suspend fun customService(machineNumber: String, customServiceCode: Int)
    suspend fun searchOccupied(): Machine?
    suspend fun findOne(number: String): Machine
    suspend fun setAutoMode(machineNumber: String, autoMode: Int)
    suspend fun getAutoMode(machineNumber: String)
}

