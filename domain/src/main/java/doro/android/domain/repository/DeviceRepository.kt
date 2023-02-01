package doro.android.domain.repository

interface DeviceRepository {
    suspend fun register(token: String): Boolean
    suspend fun unregister(): Boolean
}
