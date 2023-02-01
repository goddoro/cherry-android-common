package doro.android.domain.repository

import doro.android.domain.entity.Game

interface GameRepository {
    suspend fun fetchList(): List<Game>
}