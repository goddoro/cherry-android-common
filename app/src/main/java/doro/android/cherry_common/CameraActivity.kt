package doro.android.cherry_common

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import dagger.hilt.android.AndroidEntryPoint
import doro.cherry.rtsp.player.codec.FrameQueue
import doro.cherry.rtsp.player.widget.RtspSurfaceView
import kotlinx.coroutines.delay

@AndroidEntryPoint
class CameraActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                RtspClientScreen(
                    videoUrl = intent.extras?.getString("KEY_VIDEO_URL","") ?: "",
                    password = intent.extras?.getString("KEY_PASSWORD","") ?: ""
                )
            }
        }

        init()
    }

    private fun init() {

    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        initView()
    }

    override fun onResume() {
        super.onResume()

        initView()
    }

    private fun initView() {
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}

@Composable
private fun RtspClientScreen(
    videoUrl: String,
    password: String,
) {

    val TAG = "RtspClientScreen"
    val context = LocalContext.current


    val rtspClientView =  remember {
        RtspSurfaceView(context).apply {

            setStatusListener(object : RtspSurfaceView.RtspStatusListener {
                override fun onRtspFirstFrameRendered() {
                    Log.d(TAG, "onRtspFirstFrameRendered")
                }

                override fun onRtspStatusConnected() {
                    Log.d(TAG, "onRtspStatusConnected")
                }

                override fun onRtspStatusConnecting() {
                    Log.d(TAG, "onRtspStatusConnecting")
                }

                override fun onRtspStatusDisconnected() {
                    Log.d(TAG, "onRtspStatusDisconnected")
                }

                override fun onRtspStatusFailed(message: String?) {
                    Log.d(TAG, "onRtspStatusFailed $message")
                }

                override fun onRtspStatusFailedUnauthorized() {
                    Log.d(TAG, "onRtspStatusFailedUnauthorized")
                }
            })
        }
    }

    LaunchedEffect(videoUrl){
        rtspClientView.apply {
            stop()
            val uri = Uri.parse(videoUrl)
            val username = "admin"
            init(uri, username, password)
            start(requestVideo = true, requestAudio = true)
        }
    }


    val count = remember { mutableStateOf((0)) }
    val videoQueueSize = remember { mutableStateOf(0) }
    val audioQueueSize = remember { mutableStateOf(0) }
    val dequeueInputBufferCount = remember { mutableStateOf(0) }
    val queueInputBufferCount = remember { mutableStateOf(0) }
    val dequeueOutputBufferCount = remember { mutableStateOf(0) }
    val releaseOutputBufferCount = remember { mutableStateOf(0) }
    val currentFrame = remember { mutableStateOf<FrameQueue.Frame?>(null) }
    val isSurfaceValid = remember { mutableStateOf(false) }
    val surfaceChangeCount = remember { mutableStateOf(0) }
    val surfaceDestroyed = remember { mutableStateOf(false) }
    val infoOutputFormatChanged = remember { mutableStateOf(0) }
    val infoTryAgainLater = remember { mutableStateOf(0) }
    val outIndexNegative = remember { mutableStateOf(0) }
    val videoFrameCount = remember { mutableStateOf(0) }
    val audioFrameCount = remember { mutableStateOf(0) }
    val surfaceCreated = remember { mutableStateOf(0) }
    val surfaceChanged = remember { mutableStateOf(0) }
    val videoStart = remember { mutableStateOf(0) }
    val rtspConnected = remember { mutableStateOf(0) }
    val initVideoFrameCount = remember { mutableStateOf(0) }
    val onFrameRender = remember { mutableStateOf(0L) }

    LaunchedEffect(count.value) {
        videoQueueSize.value = rtspClientView.getVideoFrameQueue().getQueueSize()
        audioQueueSize.value = rtspClientView.getAudioFrameQueue().getQueueSize()
        dequeueInputBufferCount.value =
            rtspClientView.videoDecodeThread?.dequeueInputBufferCount ?: 0
        queueInputBufferCount.value = rtspClientView.videoDecodeThread?.queueInputBufferCount ?: 0
        dequeueOutputBufferCount.value =
            rtspClientView.videoDecodeThread?.dequeueOutputBufferCount ?: 0
        releaseOutputBufferCount.value =
            rtspClientView.videoDecodeThread?.releaseOutputBufferCount ?: 0
        currentFrame.value = rtspClientView.videoDecodeThread?.currentFrame
        isSurfaceValid.value = rtspClientView.getSurfaceIsValid()
        surfaceChangeCount.value = rtspClientView.surfaceChangeCount
        surfaceDestroyed.value = rtspClientView.surfaceDestroyed
        infoOutputFormatChanged.value =
            rtspClientView.videoDecodeThread?.infoOutputFormatChanged ?: 0
        infoTryAgainLater.value = rtspClientView.videoDecodeThread?.infoTryAgainLater ?: 0
        outIndexNegative.value = rtspClientView.videoDecodeThread?.outIndexNegative ?: 0
        videoFrameCount.value = rtspClientView.videoFrameCount
        audioFrameCount.value = rtspClientView.audioFrameCount
        initVideoFrameCount.value = rtspClientView.initVideoFrameCount
        surfaceCreated.value = rtspClientView.surfaceCreated
        surfaceChanged.value = rtspClientView.surfaceChanged
        videoStart.value = rtspClientView.videoStart
        rtspConnected.value = rtspClientView.rtspConnected
        onFrameRender.value = rtspClientView.videoDecodeThread?.onFrameRenderTime ?: 0L
        delay(200)
        count.value = count.value + 1
    }
    DisposableEffect(
        Row {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .size(200.dp)
            ) {
                AndroidView(
                    modifier = Modifier.size(200.dp), factory = {
                        rtspClientView
                    }
                )
            }
            Column {
                Text(
                    text = "Cherry Rtsp Client - 2",
                    color = Color.Red,
                    fontSize = 20.sp,
                )
                Text(
                    text = "Video Queue Size = ${videoQueueSize.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "Audio Queue Size = ${audioQueueSize.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "dequeueInputBufferCount = ${dequeueInputBufferCount.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "queueInputBufferCount = ${queueInputBufferCount.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "dequeueOutputBufferCount = ${dequeueOutputBufferCount.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "releaseOutputBufferCount = ${releaseOutputBufferCount.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )

                Text(
                    text = "Current Frame Data Size = ${currentFrame.value?.data?.size}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "infoOutputFormatChanged = ${infoOutputFormatChanged.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "infoTryAgainLater = ${infoTryAgainLater.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "outIndexNegative = ${outIndexNegative.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "video frame count = ${videoFrameCount.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "audio frame count = ${audioFrameCount.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "init video frame count = ${initVideoFrameCount.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "on frame render time = ${onFrameRender.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
//                Text(
//                    text = "Current Frame TimeStamp = ${currentFrame.value?.timestamp}",
//                    color = Color.Red,
//                    modifier = Modifier.background(Color.Green)
//                )
//                Text(
//                    text = "Current Frame length = ${currentFrame.value?.length}",
//                    color = Color.Red,
//                    modifier = Modifier.background(Color.Green)
//                )
//                Text(
//                    text = "Current Frame offset = ${currentFrame.value?.offset}",
//                    color = Color.Red,
//                    modifier = Modifier.background(Color.Green)
//                )
                Text(
                    text = "Surface is Valid = ${isSurfaceValid.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "Surface Change Count = ${surfaceChangeCount.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "Surface Destroyed = ${surfaceDestroyed.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
            }
            Column {
                Text(
                    text = "surfaceCreated order = ${surfaceCreated.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "surfaceChanged order = ${surfaceChanged.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "videoStart order = ${videoStart.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )
                Text(
                    text = "rtspConnected order = ${rtspConnected.value}",
                    color = Color.Red,
                    modifier = Modifier.background(Color.Green)
                )

            }
        }
    ) {
        onDispose {
            rtspClientView.stop()
        }
    }
}

@Composable
private fun ExoPlayerScreen(
    modifier: Modifier = Modifier,
    videoUrl: String,
) {

    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val mediaItem = MediaItem.fromUri(videoUrl)
                try {
                    setMediaItem(mediaItem)
                    playWhenReady = true
                    prepare()
                } catch (e: Exception) {

                }
            }
    }

    DisposableEffect(
        Column {
            AndroidView(modifier = Modifier.size(300.dp), factory = {
                StyledPlayerView(context).apply {
                    hideController()
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                }
            })
            Text(
                text = "ExoPlayer",
                color = Color.Red,
                fontSize = 20.sp,
            )
        }
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Composable
fun TextClockCompose(
    modifier: Modifier = Modifier,
    timeZone: String? = null,
) {
    AndroidView(
        factory = { context ->
            TextClock(context).apply {
                format12Hour?.let { this.format12Hour = "yyyy-MM-dd hh:mm:ss a" }
                format24Hour?.let { this.format24Hour = "yyyy-MM-dd hh:mm:ss" }
                timeZone?.let { this.timeZone = it }
                setTextColor(context.resources.getColor(R.color.purple_200))
            }
        },
        modifier = modifier
    )
}