package doro.android.data.repository

import doro.android.core.util.filterValueNotNull
import doro.android.data.service.UserService
import doro.android.domain.entity.User
import doro.android.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun findOne(userId: Int): User = withContext(Dispatchers.IO) {
        userService.findOne(userId).toDomain()
    }

    override suspend fun search(query: String): User? = withContext(Dispatchers.IO) {
        userService.search(query)?.user?.toDomain()
    }

    override suspend fun update(userId: Int, password: String): User = withContext(Dispatchers.IO) {
        val params = hashMapOf(
            "password" to password,
        ).filterValueNotNull()
        userService.update(userId, params).toDomain()
    }

    override suspend fun fetchAll(): List<User> = withContext(Dispatchers.IO) {
        userService.fetchAll().users.map { it.toDomain() }
    }

}