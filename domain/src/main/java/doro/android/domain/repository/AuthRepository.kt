package doro.android.domain.repository

import doro.android.domain.entity.Agent
import doro.android.domain.entity.User

interface AuthRepository {
    suspend fun signIn(email: String, password: String): User
    suspend fun signUp(email: String, password: String, username: String): Boolean
    suspend fun sendVerifyCodeEmail(email: String): Boolean
    fun logout()
    suspend fun checkValidationCode(code: String): Boolean
    suspend fun agentSignIn(name: String, password: String): Agent
}
