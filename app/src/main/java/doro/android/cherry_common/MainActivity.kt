package doro.android.cherry_common

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import dagger.hilt.android.AndroidEntryPoint
import doro.android.domain.enums.CherryUI
import doro.android.domain.repository.LogRepository
import doro.cherry.rtsp.player.widget.RtspSurfaceView
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var logRepository: LogRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                LaunchedEffect(Unit){
                    logRepository.sendEnterEvent(CherryUI.game)
                }
                Text(text = "HELLO TESTER")
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RtspClientScreen(
                        videoUrl = "rtsp://admin:Cherry11!\$@61.72.138.120:552/live"
                    )
                }
            }
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
    DisposableEffect(
        AndroidView(
            modifier = modifier, factory = {
                rtspClientView
            }
        )
    ) {
        onDispose {
            rtspClientView.stop()
        }
    }
}