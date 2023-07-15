package doro.android.cherry_common

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import doro.cherry.rtsp.player.codec.FrameQueue
import doro.cherry.rtsp.player.widget.RtspSurfaceView
import kotlinx.coroutines.delay
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout


@AndroidEntryPoint
class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoUrl = intent?.getStringExtra("KEY_VIDEO_URL") ?: ""
        val password = intent?.getStringExtra("KEY_PASSWORD") ?: ""
        setContent {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                //VLCPlayer(videoUrl = videoUrl )
                RtspClientScreen(videoUrl = videoUrl, password = password)
            }
        }
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
fun VLCPlayer(
    modifier: Modifier = Modifier,
    videoUrl: String
) {
    val context = LocalContext.current
    // 비디오 레이아웃
    val mVideoLayout: VLCVideoLayout = VLCVideoLayout(context)
    // LibVLC 클래스
    var mLibVLC: LibVLC? = null
    // 미디어 컨트롤러
    var mMediaPlayer: MediaPlayer? = null

    LaunchedEffect(Unit) {
        val args: ArrayList<String> = ArrayList()
        args.add("-vvv")

        mLibVLC = LibVLC(context, args)
        mMediaPlayer = MediaPlayer(mLibVLC)
        mMediaPlayer!!.setVideoTrackEnabled(true)
        mVideoLayout.let { mMediaPlayer!!.attachViews(it, null, true, true) }
        val media = Media(mLibVLC, android.net.Uri.parse(videoUrl))
        // 미디어 컨트롤러 클래스에 미디어 적용
        mMediaPlayer!!.media = media
        mMediaPlayer!!.play()
    }


    DisposableEffect(
        AndroidView(
            modifier = modifier, factory = {
                mVideoLayout
            }
        ) {

        }) {
        onDispose {
            mMediaPlayer?.release()
        }
    }


}


@Composable
private fun RtspClientScreen(
    videoUrl: String,
    password: String,
) {

    val TAG = "RtspClientScreen"
    val context = LocalContext.current

    val rtspClientView = remember {
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

    LaunchedEffect(videoUrl) {
        rtspClientView.apply {
            stop()
            val uri = android.net.Uri.parse(videoUrl)
            val username = "admin"
            init(uri, username, password)
            start(requestVideo = true, requestAudio = true)
        }
    }


    val count = remember { mutableStateOf((0)) }
    val codecName = remember { mutableStateOf("") }
    val mimeType = remember { mutableStateOf("") }
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
        codecName.value = rtspClientView.videoDecodeThread?.decoder?.codecInfo?.name.orEmpty()
        mimeType.value = rtspClientView.videoDecodeThread?.mimeType.toString()
        videoQueueSize.value = rtspClientView.getVideoFrameQueue().getQueueSize()
        audioQueueSize.value = rtspClientView.getAudioFrameQueue().getQueueSize()
        dequeueInputBufferCount.value =
            rtspClientView.videoDecodeThread?.dequeueInputBufferCount ?: 0
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
        AndroidView(
            factory = {
                rtspClientView
            }
        ) {

        }) {
        onDispose {
            rtspClientView.stop()
        }
    }
}