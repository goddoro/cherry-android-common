package doro.android.data.repository

import doro.android.data.service.UsageHistoryService
import doro.android.domain.entity.UsageHistory
import doro.android.domain.repository.UsageHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsageHistoryRepositoryImpl @Inject constructor(
    private val usageHistoryService: UsageHistoryService
) : UsageHistoryRepository {

    override suspend fun fetch(startDate: String, endDate: String): List<UsageHistory> = withContext(Dispatchers.IO) {
        usageHistoryService.fetch(startDate, endDate).usageHistories.map { it.toDomain() }
    }
}