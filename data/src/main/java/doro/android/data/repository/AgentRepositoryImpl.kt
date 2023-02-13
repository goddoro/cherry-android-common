package doro.android.data.repository

import doro.android.data.service.AgentService
import doro.android.domain.entity.Agent
import doro.android.domain.repository.AgentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AgentRepositoryImpl @Inject constructor(
    private val agentService: AgentService
) : AgentRepository {

    override suspend fun findOne(name: String): Agent =
        withContext(Dispatchers.IO) {
            agentService.searchOne(name).toDomain()
        }
}