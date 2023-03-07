package doro.android.data.repository

import doro.android.data.dto.CommissionApproveRequest
import doro.android.data.service.CommissionService
import doro.android.domain.entity.Commission
import doro.android.domain.entity.CommissionStatus
import doro.android.domain.repository.CommissionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommissionRepositoryImpl @Inject constructor(
    private val commissionService: CommissionService,
) : CommissionRepository {

    override suspend fun fetch(agentId: Int?): List<Commission> = withContext(Dispatchers.IO) {
        commissionService.fetch(agentId).commissions.map { it.toDomain() }
    }

    override suspend fun approve(status: CommissionStatus, ids: List<Int>): Boolean =
        withContext(Dispatchers.IO) {
            commissionService.approve(
                body = CommissionApproveRequest(status = status, ids = ids)
            ).success
        }
}