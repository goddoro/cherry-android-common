package doro.android.domain.repository

import doro.android.domain.entity.AgentPointRequest
import doro.android.domain.entity.PointRequestType
import doro.android.domain.entity.UserPointRequest

interface UserPointRequestRepository {
    suspend fun findList(
        startDate: String,
        endDate: String,
        userId: Int? = null,
        agentId: Int? = null
    ): List<UserPointRequest>

    suspend fun findAgentList(
        startDate: String,
        endDate: String,
        agentId: Int? = null
    ): AgentPointRequest

    suspend fun findOne(id: Int): UserPointRequest
    suspend fun carry(id: Int): Boolean
    suspend fun create(agentName: String, point: Int, money: Int, type: PointRequestType): Boolean
    suspend fun updateStatus(id: Int, status: String)
}
