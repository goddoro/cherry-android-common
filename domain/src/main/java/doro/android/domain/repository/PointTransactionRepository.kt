package doro.android.domain.repository

import doro.android.domain.entity.PointRequestType
import doro.android.domain.entity.UserPointRequest
import doro.android.domain.entity.UserPointTransaction

interface PointTransactionRepository {
    suspend fun fetch(): List<UserPointTransaction>
}