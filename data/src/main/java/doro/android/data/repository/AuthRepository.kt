package doro.android.data.repository

import doro.android.core.util.UserHolder
import doro.android.data.service.AuthService
import doro.android.domain.entity.User
import doro.android.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val userHolder: UserHolder,
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): User = withContext(Dispatchers.IO) {
        val params = hashMapOf<String,String>().apply {
            this["email"] = email
            this["password"] = password
        }
        val signInResponse = authService.signIn(params)
        signInResponse.user.toDomain(signInResponse.token)
    }

    override suspend fun signUp(email: String, password: String, username: String): Boolean = withContext(Dispatchers.IO){
        val params = hashMapOf<String,String>().apply {
            this["email"] = email
            this["password"] = password
            this["username"] = username
        }
        authService.signUp(params).success
    }

    override suspend fun sendVerifyCodeEmail(email: String): Boolean = withContext(Dispatchers.IO){
        val params = hashMapOf<String,String>().apply {
            this["email"] = email
        }
        authService.sendVerifyCodeEmail(params).success
    }

    override suspend fun checkValidationCode(code: String): Boolean = withContext(Dispatchers.IO){
        val params = hashMapOf<String,String>().apply {
            this["code"] = code
        }
        authService.verifyCode(params).success
    }


    override fun logout() {
        userHolder.clearUser()
    }
}