package doro.android.domain.repository

import doro.android.domain.entity.PointRequestType
import doro.android.domain.entity.UserPointRequest
import doro.android.domain.entity.UserPointTransaction

interface PointTransactionRepository {
    suspend fun request(agentName: String, point: Int, money: Int, type: PointRequestType): Boolean
    suspend fun fetch(): List<UserPointTransaction>
    suspend fun fetchRequest(userId: Int? = null, agentId : Int? = null): List<UserPointRequest>
}