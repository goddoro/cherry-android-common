package doro.android.data.repository

import doro.android.data.service.AgentService
import doro.android.data.service.AgentSignUpRequest
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

    override suspend fun findOne(id: Int): Agent = withContext(Dispatchers.IO) {
        agentService.findOne(id).toDomain()
    }

    override suspend fun signUp(
        nickName: String,
        email: String,
        password: String,
        telephone: String,
        location: String
    ): Unit = withContext(Dispatchers.IO) {
        val request = AgentSignUpRequest(
            nickName = nickName,
            email = email,
            password = password,
            telephone = telephone,
            location = location
        )
        agentService.signUp(request)
    }
}
