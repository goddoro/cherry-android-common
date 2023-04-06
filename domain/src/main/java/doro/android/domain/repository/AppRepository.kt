package doro.android.domain.repository

interface AppRepository {
    suspend fun getServerVersion(): String
}