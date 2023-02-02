import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HoldSlotValue(
    val cameraUrl: String,
    val streamUrl: String,
    val machineNumber: String,
    val credit: Int,
) : Parcelable