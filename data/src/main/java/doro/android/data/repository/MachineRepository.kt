package doro.android.data.repository

import android.os.Parcelable
import doro.android.core.util.SoundEffectPlayer
import doro.android.data.service.MachineCommandRequest
import doro.android.data.service.MachineService
import doro.android.domain.entity.GameButton
import doro.android.domain.entity.Machine
import doro.android.domain.enums.CherryGameButtonName
import doro.android.domain.repository.LogRepository
import doro.android.domain.repository.MachineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class MachineRepositoryImpl @Inject constructor(
    private val machineService: MachineService,
    private val logRepository: LogRepository,
    private val soundEffectPlayer: SoundEffectPlayer,
) : MachineRepository {
    override suspend fun fetchList(): List<Machine> =
        withContext(Dispatchers.IO) {
            machineService.findList().machines.map { it.toDomain() }
        }

    override suspend fun creditIn(machineNumber: String, credit: Int) =
        withContext(Dispatchers.IO) {
            logRepository.sendGameButtonEvent(
                name = CherryGameButtonName.credit_in.name,
                credit = credit,
            )
            val request = MachineCommandRequest(
                endPoint = "IC",
                machineNumber = machineNumber,
                credit = credit
            )
            machineService.command(request)
        }

    override suspend fun creditOut(machineNumber: String, credit: Int) =
        withContext(Dispatchers.IO) {
            logRepository.sendGameButtonEvent(
                name = CherryGameButtonName.credit_in.name,
                credit = credit,
            )
            val request = MachineCommandRequest(
                endPoint = "OC",
                machineNumber = machineNumber,
                credit = credit
            )
            machineService.command(request)
        }

    override suspend fun holdSlot(machineNumber: String) = withContext(Dispatchers.IO) {
        val request = MachineCommandRequest(
            endPoint = "HS",
            machineNumber = machineNumber,
        )
        machineService.command(request)
    }

    override suspend fun pressButton(machineNumber: String, button: GameButton) =
        withContext(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                soundEffectPlayer.play()
            }
            logRepository.sendGameButtonEvent(
                name = button.name.lowercase()
            )
            val request = MachineCommandRequest(
                endPoint = "PB",
                machineNumber = machineNumber,
                buttonNumber = button.number,
            )
            machineService.command(request)
        }

    override suspend fun selectEvent(machineNumber: String, eventNumber: Int) {
        withContext(Dispatchers.IO){
            val request = MachineCommandRequest(
                endPoint = "SE",
                machineNumber = machineNumber,
                eventNumber = eventNumber,
            )
            machineService.command(request)
        }
    }

    override suspend fun customService(machineNumber: String, customServiceCode: Int) {
        withContext(Dispatchers.IO){
            val request = MachineCommandRequest(
                endPoint = "CS",
                machineNumber = machineNumber,
                customServiceCode = customServiceCode,
            )
            machineService.command(request)
        }
    }

    override suspend fun searchOccupied(): String? =
        withContext(Dispatchers.IO) {
            machineService.searchOccupied().number
        }

    override suspend fun releaseSlot(machineNumber: String) =
        withContext(Dispatchers.IO) {
            val request = MachineCommandRequest(
                endPoint = "RS",
                machineNumber = machineNumber,
            )
            machineService.command(request)
        }

    override suspend fun findOne(number: String): Machine =
        withContext(Dispatchers.IO) {
            machineService.findOne(number).toDomain()
        }

    override suspend fun getAddress(machineNumber: String) =
        withContext(Dispatchers.IO) {
            val request = MachineCommandRequest(
                endPoint = "GA",
                machineNumber = machineNumber
            )
            machineService.command(request)
        }

    override suspend fun getAutoMode(machineNumber: String) {
        withContext(Dispatchers.IO){
            val request = MachineCommandRequest(
                endPoint = "AS",
                machineNumber = machineNumber,
            )
            machineService.command(request)
        }
    }

    override suspend fun setAutoMode(machineNumber: String, autoMode: Int) {
        withContext(Dispatchers.IO){
            val request = MachineCommandRequest(
                endPoint = "AM",
                machineNumber = machineNumber,
                autoMode = autoMode,
            )
            machineService.command(request)
        }
    }
}


@Parcelize
data class HoldSlotResponse(
    val cameraUrl: String,
    val streamUrl: String,
    val machineNumber: String,
    val credit: Int,
) : Parcelable