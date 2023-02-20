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
                endPoint = "credit-in",
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
                endPoint = "credit-out",
                machineNumber = machineNumber,
                credit = credit
            )
            machineService.command(request)
        }

    override suspend fun holdSlot(machineNumber: String) = withContext(Dispatchers.IO) {
        val request = MachineCommandRequest(
            endPoint = "hold-slot",
            machineNumber = machineNumber,
        )
        machineService.command(request)
    }

    override suspend fun pressButton(machineNumber: String, button: GameButton) =
        withContext(Dispatchers.IO) {
            soundEffectPlayer.play()
            logRepository.sendGameButtonEvent(
                name = button.name.lowercase()
            )
            val request = MachineCommandRequest(
                endPoint = "press-button",
                machineNumber = machineNumber,
                buttonNumber = button.number,
            )
            machineService.command(request)
        }

    override suspend fun searchOccupied(): Machine =
        withContext(Dispatchers.IO) {
            machineService.searchOccupied().toDomain()
        }

    override suspend fun releaseSlot(machineNumber: String) =
        withContext(Dispatchers.IO) {
            val request = MachineCommandRequest(
                endPoint = "release-slot",
                machineNumber = machineNumber,
            )
            machineService.command(request)
        }

    override suspend fun findOne(number: String): Machine =
        withContext(Dispatchers.IO) {
            machineService.findOne(number).toDomain()
        }
}

@Parcelize
data class HoldSlotResponse(
    val cameraUrl: String,
    val streamUrl: String,
    val machineNumber: String,
    val credit: Int,
) : Parcelable