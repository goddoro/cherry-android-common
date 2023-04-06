package doro.android.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FaqListResponse(
    @SerializedName("faqs")
    val faqs: List<FaqResponse>
): Parcelable