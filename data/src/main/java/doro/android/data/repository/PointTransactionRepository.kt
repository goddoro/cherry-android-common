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
}