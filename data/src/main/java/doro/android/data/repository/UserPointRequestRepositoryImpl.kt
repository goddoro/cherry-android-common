package doro.android.data.repository

import doro.android.data.dto.CreatePointRequest
import doro.android.data.dto.UserPointRequestCarryRequest
import doro.android.data.service.UserPointRequestService
import doro.android.domain.entity.AgentPointRequest
import doro.android.domain.entity.PointRequestType
import doro.android.domain.entity.UserPointRequest
import doro.android.domain.repository.UserPointRequestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserPointRequestRepositoryImpl @Inject constructor(
    private val userPointRequestService: UserPointRequestService,
) : UserPointRequestRepository {
    override suspend fun findList(
        startDate: String,
        endDate: String,
        userId: Int?,
        agentId: Int?,
    ): List<UserPointRequest> = withContext(Dispatchers.IO) {
        userPointRequestService.fetchList(
            startDate = startDate,
            endDate = endDate,
            userId = userId,
            agentId = agentId
        ).userPointRequests.map { it.toDomain() }
    }

    override suspend fun findAgentList(
        startDate: String,
        endDate: String,
        agentId: Int?
    ): AgentPointRequest = withContext(Dispatchers.IO){
        userPointRequestService.fetchAgentList(
            startDate = startDate,
            endDate = endDate,
            userId = 0,
            agentId = agentId
        ).toDomain()
    }

    override suspend fun findOne(id: Int): UserPointRequest = withContext(Dispatchers.IO) {
        userPointRequestService.findOne(id).toDomain()
    }

    override suspend fun carry(id: Int): Boolean = withContext(Dispatchers.IO) {
        userPointRequestService.carry(UserPointRequestCarryRequest(id)).success
    }

    override suspend fun create(
        agentName: String,
        point: Int,
        money: Int,
        type: PointRequestType
    ): Boolean = withContext(Dispatchers.IO) {
        val userPointRequest = CreatePointRequest(
            agentName = agentName,
            point = point,
            money = money,
            type = type,
        )
        userPointRequestService.create(userPointRequest).success
    }
}