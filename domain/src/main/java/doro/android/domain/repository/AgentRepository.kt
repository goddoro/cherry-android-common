package doro.android.domain.repository

import doro.android.domain.entity.Agent

interface AgentRepository {
    suspend fun findOne(name: String): Agent
}