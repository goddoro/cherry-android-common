package doro.android.cherry_common

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import dagger.hilt.android.AndroidEntryPoint
import doro.android.domain.repository.LogRepository
import doro.cherry.rtsp.player.widget.RtspSurfaceView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.Socket
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var logRepository: LogRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier.size(400.dp)
            ) {
                val videoUrl =
                    remember { mutableStateOf("rtsp://admin:Cherry11!\$@61.72.138.120:552/live") }
                Row {


                    RtspClientScreen(
                        videoUrl = videoUrl.value,
                        logRepository = logRepository,
                    )

                }
                Text(text = "on/off", modifier = Modifier.clickable {
                    if (videoUrl.value == ""){
                        videoUrl.value = "rtsp://admin:Cherry11!\$@61.72.138.120:552/live"
                    } else {
                        videoUrl.value = ""
                    }

                }, color = Color.Yellow, fontSize = 30.sp)
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
    modifier: Modifier = Modifier,
    videoUrl: String,
    logRepository: LogRepository,
) {

    val TAG = "RtspClientScreen"
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    val rtspClientView = remember {
        RtspSurfaceView(context).apply {
            val uri = Uri.parse(videoUrl)
            val username = "admin"
            val password = "Cherry11!$"
            init(uri, username, password)
            start(requestVideo = true, requestAudio = true)
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
    val count = remember { mutableStateOf((0))}
    val videoQueueSize = remember { mutableStateOf(0)}
    val audioQueueSize = remember { mutableStateOf(0)}
    LaunchedEffect(count.value){
        videoQueueSize.value = rtspClientView.getVideoFrameQueue().getQueueSize()
        audioQueueSize.value = rtspClientView.getAudioFrameQueue().getQueueSize()
        delay(500)
        count.value = count.value + 1
    }
    DisposableEffect(
        Column(
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.White)
            ) {
                AndroidView(
                    modifier = modifier, factory = {
                        rtspClientView
                    }
                )
            }
            Text(
                text = "Cherry Rtsp Client",
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
                } catch( e: Exception){

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