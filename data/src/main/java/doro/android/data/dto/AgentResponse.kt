package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.Agent
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,
): Parcelable{
    fun toDomain(): Agent {
        return Agent(
            id = this.id,
            name = this.name,
        )
    }
}
