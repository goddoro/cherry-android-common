package doro.android.domain.repository

import doro.android.domain.entity.UsageHistory

interface UsageHistoryRepository {
    suspend fun fetch(startDate: String, endDate: String): List<UsageHistory>
    suspend fun findOne(id: Int): UsageHistory
}