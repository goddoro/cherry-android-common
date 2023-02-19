package doro.android.domain.repository

import doro.android.domain.entity.PointRequestType
import doro.android.domain.entity.UserPointRequest

interface UserPointRequestRepository {
    suspend fun findList(userId: Int? = null, agentId: Int? = null, type: PointRequestType): List<UserPointRequest>
    suspend fun findOne(id: Int): UserPointRequest
    suspend fun carry(id: Int): Boolean
    suspend fun create(agentName: String, point: Int, money: Int, type: PointRequestType): Boolean
}