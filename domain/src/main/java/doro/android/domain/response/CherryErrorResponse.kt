package doro.android.domain.response

import android.content.Context
import android.os.Parcelable
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import doro.android.domain.R
import kotlinx.parcelize.Parcelize
import retrofit2.HttpException

@Parcelize
data class CherryErrorResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String
) : ErrorResponse(), Parcelable

open class ErrorResponse {

    fun printErrorMessage(context: Context): String {

        return when (this) {
            is CherryErrorResponse -> {
                this.message
            }
            else -> {
                context.getString(R.string.unstable_network_message)
            }
        }
    }

    companion object {
        fun parseThrowable(throwable: Throwable): ErrorResponse {

            return if (throwable is HttpException) {

                Gson().fromJson(
                    throwable.response()?.errorBody()?.string(),
                    CherryErrorResponse::class.java
                )

            } else {
                Log.d("CherryErrorResponse", throwable.message.orEmpty())
                ErrorResponse()
            }
        }
    }
}