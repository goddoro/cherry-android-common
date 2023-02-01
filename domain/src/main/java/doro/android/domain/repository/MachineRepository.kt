package doro.android.domain.repository

import doro.android.domain.entity.Machine

interface MachineRepository {
    suspend fun fetchList(gameId: Int): List<Machine>
    suspend fun creditIn(machineNumber: String, point: Int)
    suspend fun creditOut(machineNumber: String, point: Int)
    suspend fun holdSlot(machineNumber: String)
    suspend fun releaseSlot(machineNumber: String)
    suspend fun pressButton(machineNumber: String, buttonNumber: Int)
    suspend fun searchOccupied(): Machine
    suspend fun findOne(number: String): Machine
}

