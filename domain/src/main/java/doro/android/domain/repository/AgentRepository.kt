package doro.android.domain.repository

import doro.android.domain.entity.Agent

interface AgentRepository {
    suspend fun findOne(name: String): Agent
    suspend fun findOne(id: Int): Agent

    suspend fun signUp(
        nickName: String,
        email: String,
        password: String,
        telephone: String,
        location: String
    )
}
