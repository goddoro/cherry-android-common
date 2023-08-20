package doro.android.data.service

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Notification
import kotlinx.parcelize.Parcelize
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationService {
    @GET("/notifications")
    suspend fun findList(@Query("page") page: Int): NotificationListResponse
}

@Parcelize
data class NotificationListResponse(
    @SerializedName("notifications")
    val notifications: List<NotificationResponse>
): Parcelable

@Parcelize
data class NotificationResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("body")
    val body: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("createdAt")
    val createdAt: String,
) : Parcelable {
    fun toDomain() = Notification(
        id = id,
        body = body,
        title = title,
        createdAt = createdAt,
    )
}
