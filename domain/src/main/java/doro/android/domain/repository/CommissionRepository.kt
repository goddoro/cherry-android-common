package doro.android.domain.repository

import doro.android.domain.entity.Commission
import doro.android.domain.entity.CommissionStatus

interface CommissionRepository {

    suspend fun fetch(agentId: Int? = null): List<Commission>
    suspend fun approve(status: CommissionStatus, ids: List<Int>): Boolean
}