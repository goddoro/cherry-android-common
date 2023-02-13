package doro.android.data.repository

import doro.android.data.dto.CreatePointRequest
import doro.android.data.service.PointTransactionService
import doro.android.domain.entity.UserPointRequest
import doro.android.domain.entity.UserPointTransaction
import doro.android.domain.repository.PointTransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointTransactionRepositoryImpl @Inject constructor(
    private val pointTransactionService: PointTransactionService,
) : PointTransactionRepository {

    override suspend fun fetch(): List<UserPointTransaction> = withContext(Dispatchers.IO) {
        pointTransactionService.fetch().userPointTransactions.map { it.toDomain() }
    }

    override suspend fun request(
        agentName: String,
        point: Int,
        money: Int,
        type: doro.android.domain.entity.PointRequestType
    ): Boolean =
        withContext(Dispatchers.IO) {
            val userPointRequest = CreatePointRequest(
                agentName = agentName,
                point = point,
                money = money,
                type = type,
            )
            pointTransactionService.request(userPointRequest).success
        }

    override suspend fun fetchRequest(userId: Int?, agentId: Int?): List<UserPointRequest> =
        withContext(Dispatchers.IO) {
            pointTransactionService.fetchRequest(
                userId,
                agentId
            ).userPointRequests.map { it.toDomain() }
        }
}