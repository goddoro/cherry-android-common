package doro.android.cherry_common

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import dagger.hilt.android.AndroidEntryPoint
import doro.cherry.rtsp.player.widget.RtspSurfaceView
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RtspClientScreen(
                videoUrl = "rtsp://admin:Cherry11!\$@61.72.138.120:552/live"
            )
        }
    }
}

@Composable
private fun RtspClientScreen(
    modifier: Modifier = Modifier,
    videoUrl: String,
) {

    val TAG = "RtspClientScreen"
    val context = LocalContext.current

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
        Log.d("GOOD", videoQueueSize.value.toString())
        Log.d("GOOD", audioQueueSize.value.toString())
        videoQueueSize.value = rtspClientView.getVideoFrameQueue().getQueueSize()
        audioQueueSize.value = rtspClientView.getAudioFrameQueue().getQueueSize()
        delay(500)
        count.value = count.value + 1
    }
    DisposableEffect(
        Column(
            modifier = Modifier.background(Color.Yellow)
        ) {
            Text(text = "HELLO TESTER1", color = Color.Red)
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier.size(300.dp).background(Color.White)
            ) {
                AndroidView(
                    modifier = modifier, factory = {
                        rtspClientView
                    }
                )
            }
            Text(text = "Video Queue Size = ${videoQueueSize.value}", color = Color.Red, modifier = Modifier.background(Color.Green))
            Text(text = "Audio Queue Size = ${audioQueueSize.value}", color = Color.Red, modifier = Modifier.background(Color.Green))
        }
    ) {
        onDispose {
            rtspClientView.stop()
        }
    }
}