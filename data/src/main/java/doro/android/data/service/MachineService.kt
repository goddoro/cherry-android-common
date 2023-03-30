package doro.android.data.service

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.data.dto.*
import kotlinx.parcelize.Parcelize
import retrofit2.http.*

interface MachineService {
    @GET("/machines/{number}")
    suspend fun findOne(@Path("number") number : String) : MachineResponse

    @GET("/machines")
    suspend fun findList(): MachineListResponse

    @POST("/machines/command")
    suspend fun command(@Body request: MachineCommandRequest)

    @GET("/machines/search/occupied")
    suspend fun searchOccupied(): MachineResponse

}

@Parcelize
data class MachineCommandRequest(
    @SerializedName("endPoint")
    val endPoint: String,

    @SerializedName("machineNumber")
    val machineNumber: String,

    @SerializedName("buttonNumber")
    val buttonNumber: Int? = null,

    @SerializedName("credit")
    val credit: Int? = null,

    @SerializedName("eventNumber")
    val eventNumber: Int? = null,

    @SerializedName("customServiceCode")
    val customServiceCode: Int? = null,

    @SerializedName("autoMode")
    val autoMode: Int? = null,
): Parcelable
