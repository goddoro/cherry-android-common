package doro.android.domain.entity

data class DebugModeItem(
    val videoCodec: String,
    val audioCodec: String,
    val videoMimeType: String,
    val audioMimeType: String,
    val videoInputBufferCount: Int,
    val videoOutputBufferCount: Int,
    val videoRenderedBufferCount: Int,
    val audioInputBufferCount: Int,
    val audioOutputBufferCount: Int,
    val audioRenderedBufferCount: Int,
    val videoBufferSize: Int,
    val audioBufferSize: Int,
    val videoTimeStamp: Long,
    val audioTimeStamp: Long,
    val serverName: String,
    val serverUrl: String,
    val bitrate: String = "Not Implemented Yet",
    val bandwidth: String = "Not Implemented Yet"
)
