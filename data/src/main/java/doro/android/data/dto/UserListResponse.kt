package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.entity.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserListResponse(
    @SerializedName("users")
    val users: List<UserResponse>
): Parcelable
