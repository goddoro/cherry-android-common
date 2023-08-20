package doro.android.data.repository

import doro.android.data.service.NotificationService
import doro.android.domain.entity.Notification
import doro.android.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationRepository {

    override suspend fun findList(): List<Notification> = withContext(Dispatchers.IO) {
        notificationService.findList(1).notifications.map { it.toDomain() }
    }
}