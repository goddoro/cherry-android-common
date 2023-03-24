package doro.android.cherry_common

import android.content.Intent
import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.os.Bundle
import android.util.Log
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

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)

        // 모든 코덱을 배열로 가져오기

        // 모든 코덱을 배열로 가져오기
        val codecs: Array<MediaCodecInfo> = codecList.codecInfos

        // 코덱 정보 출력

        // 코덱 정보 출력
        for (codec in codecs) {
            if (codec.isEncoder) {
                //Log.d("CodecList", codec.name + " (Encoder)")
            } else {
                Log.d("CodecList", codec.name + " (Decoder)")
            }
        }

        setContent {
            Column(
                modifier = Modifier.background(Color.White),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text("1번 스트림 영상", fontSize = 40.sp, color = Color.Red, modifier = Modifier
                    .clickable {
                        onClick("rtsp://admin:Cherry11!$@61.72.138.120:552/live", "Cherry11!$")
                    }
                    .background(Color.Green))


                Text("1번 카메라 영상", fontSize = 40.sp, color = Color.Red, modifier = Modifier
                    .clickable {
                        onClick(
                            "rtsp://admin:Cherry11!$@61.72.138.120:553/Streaming/Channels/101?transportmode=unicast&profile=Profile_1",
                            "Cherry11!$"
                        )
                    }
                    .background(Color.Green))


                Text("2번 스트림 영상", fontSize = 40.sp, color = Color.Red, modifier = Modifier
                    .clickable {
                        onClick("rtsp://admin:Cherry11!$@61.72.138.120:551/live", "Cherry11!$")
                    }
                    .background(Color.Green))


                Text("2번 카메라 영상", fontSize = 40.sp, color = Color.Red, modifier = Modifier
                    .clickable {
                        onClick(
                            "rtsp://admin:Cherry01!$@61.72.138.120:555/Streaming/Channels/101?transportmode=unicast&profile=Profile_1",
                            "Cherry01!$"
                        )
                    }
                    .background(Color.Green))

            }

        }
    }

    private fun onClick(video: String, password: String) {
        val intent = Intent(this@MainActivity, CameraActivity::class.java)
        intent.putExtra("KEY_VIDEO_URL", video)
        intent.putExtra("KEY_PASSWORD", password)
        startActivity(intent)
    }


}

