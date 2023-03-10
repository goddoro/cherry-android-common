package doro.android.data.repository

import doro.android.data.dto.JumbotronCreateRequest
import doro.android.data.service.JumbotronService
import doro.android.domain.entity.Jumbotron
import doro.android.domain.repository.JumbotronRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JumbotronRepositoryImpl @Inject constructor(
    private val jumbotronService: JumbotronService,
) : JumbotronRepository {
    override suspend fun create(title: String, body: String, gameId: Int?) =
        withContext(Dispatchers.IO) {
            val request = JumbotronCreateRequest(
                title = title,
                body = body,
                gameId = gameId,
            )
            jumbotronService.create(request)
        }

    override suspend fun fetchList(): List<Jumbotron> = withContext(Dispatchers.IO) {
        jumbotronService.fetchList().jumbotrons.map { it.toDomain() }
    }
}

