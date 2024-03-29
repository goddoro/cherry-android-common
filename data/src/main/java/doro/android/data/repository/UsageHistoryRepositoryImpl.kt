package doro.android.data.repository

import doro.android.core.util.UserHolder
import doro.android.data.service.UsageHistoryService
import doro.android.domain.entity.UsageHistory
import doro.android.domain.repository.UsageHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsageHistoryRepositoryImpl @Inject constructor(
    private val usageHistoryService: UsageHistoryService,
    private val userHolder: UserHolder
) : UsageHistoryRepository {

    override suspend fun findOne(id: Int): UsageHistory= withContext(Dispatchers.IO) {
        usageHistoryService.findOne(id).toDomain()
    }
    override suspend fun fetch(startDate: String, endDate: String): List<UsageHistory> = withContext(Dispatchers.IO) {
        usageHistoryService.fetch(startDate, endDate, userHolder.getUserId()).usageHistories.map { it.toDomain() }
    }
}