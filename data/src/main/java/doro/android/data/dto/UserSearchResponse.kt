package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSearchResponse(
    @SerializedName("user")
    val user: UserResponse?
) : Parcelable