package doro.android.data.dto

import android.content.Context
import android.os.Parcelable
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import doro.android.data.R
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


@Parcelize
data class BadRequestResponse(
    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("message")
    val message: List<String>,

    @SerializedName("error")
    val error: String
) : ErrorResponse(), Parcelable

open class ErrorResponse {

    fun printErrorMessage(context: Context): String {

        return when (this) {
            is BadRequestResponse -> {
                this.error
            }
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
                if (throwable.message == "HTTP 400 Bad Request") {
                    Gson().fromJson(
                        throwable.response()?.errorBody()?.string(),
                        BadRequestResponse::class.java
                    )
                } else {
                    Gson().fromJson(
                        throwable.response()?.errorBody()?.string(),
                        CherryErrorResponse::class.java
                    )
                }
            } else {
                Log.d("CherryErrorResponse", throwable.message.orEmpty())
                ErrorResponse()
            }
        }
    }
}