package doro.android.data.repository

import doro.android.data.service.DeviceService
import doro.android.domain.repository.DeviceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val deviceService: DeviceService
) : DeviceRepository {

    override suspend fun register(token: String): Boolean = withContext(Dispatchers.IO) {
        val params = hashMapOf(
            "token" to token,
            "name" to android.os.Build.MODEL
        )
        deviceService.register(params).success
    }

    override suspend fun unregister(): Boolean = withContext(Dispatchers.IO){
        deviceService.unregister().success
    }
}