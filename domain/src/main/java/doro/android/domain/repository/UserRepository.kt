package doro.android.domain.repository

import doro.android.domain.entity.User

interface UserRepository {
    suspend fun fetchAll(): List<User>
    suspend fun findOne(userId: Int): User
    suspend fun search(query: String): User?
    suspend fun update(userId: Int, password: String): User
    suspend fun signOut(): Boolean
    suspend fun updatePoint(userId: Int, point: Int): User

    suspend fun updateAgent(userId: Int, agentId: Int): User
}