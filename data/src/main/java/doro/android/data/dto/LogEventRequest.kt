package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import doro.android.domain.enums.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class CherryAndroidBugEvent(
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String,
) : Parcelable

@Parcelize
data class LogEventRequest(
    @SerializedName("where")
    val where: CherryUI,
    @SerializedName("action")
    val action: CherryAction,
    @SerializedName("data")
    val data: CherryActionData? = null,
) : Parcelable

@Parcelize
data class ButtonClickData(
    @SerializedName("name")
    val name: CherryButtonEvent,
) : Parcelable, CherryActionData()

@Parcelize
data class GamePingData(
    @SerializedName("machine")
    val machineNumber: String,
    @SerializedName("credit")
    val credit: Int,
    @SerializedName("point")
    val point: Int,
    @SerializedName("game")
    val gameName: String,
) : Parcelable, CherryActionData()

@Parcelize
data class GameButtonClickData(
    @SerializedName("name")
    val name: String,
    @SerializedName("credit")
    val credit: Int? = null,
    @SerializedName("cameraMode")
    val cameraMode: String? = null,
    @SerializedName("machine")
    val machineNumber: String? = null,
) : Parcelable, CherryActionData()
