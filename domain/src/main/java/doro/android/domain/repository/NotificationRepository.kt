package doro.android.domain.repository

import doro.android.domain.entity.Notification

interface NotificationRepository {
    suspend fun findList(): List<Notification>
}