package doro.android.data.repository

import doro.android.data.service.AppService
import doro.android.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appService: AppService
): AppRepository {

    override suspend fun getServerVersion(): String = withContext(Dispatchers.IO){
        appService.getVersion().version
    }
}