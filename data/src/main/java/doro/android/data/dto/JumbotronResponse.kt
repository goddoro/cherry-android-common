package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Jumbotron
import kotlinx.parcelize.Parcelize

@Parcelize
data class JumbotronResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null,
    @SerializedName("createdAt")
    val createdAt: Int,
): Parcelable {

    fun toDomain(): Jumbotron {
        return Jumbotron(
            title = title,
            body = body,
            thumbnailUrl = thumbnailUrl.orEmpty(),
            createdAt = createdAt,
        )
    }
}
