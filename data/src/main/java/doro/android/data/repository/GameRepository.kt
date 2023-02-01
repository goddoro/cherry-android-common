package doro.android.data.repository

import doro.android.data.service.GameService
import doro.android.domain.entity.Game
import doro.android.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameService: GameService
) : GameRepository {

    override suspend fun fetchList(): List<Game> = withContext(Dispatchers.IO) {
        gameService.findList().games.map { it.toDomain() }
    }
}