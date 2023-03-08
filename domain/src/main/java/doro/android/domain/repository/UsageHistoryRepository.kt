package doro.android.domain.repository

import doro.android.domain.entity.UsageHistory

interface UsageHistoryRepository {
    suspend fun fetch(): List<UsageHistory>
}