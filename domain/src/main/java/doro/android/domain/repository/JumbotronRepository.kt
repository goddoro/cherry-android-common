package doro.android.domain.repository

import doro.android.domain.entity.Jumbotron

interface JumbotronRepository {
    suspend fun create(title: String, body: String, gameId : Int? = null)
    suspend fun fetchList(): List<Jumbotron>
}