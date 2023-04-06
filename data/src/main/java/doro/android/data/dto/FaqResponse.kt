package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Faq
import kotlinx.parcelize.Parcelize

@Parcelize
data class FaqResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
) : Parcelable {
    fun toDomain(): Faq {
        return Faq(
            id = id,
            title = title,
            description = description,
        )
    }
}
