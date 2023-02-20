package doro.android.core.util

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import doro.android.core.R
import javax.inject.Inject

class SoundEffectPlayer @Inject constructor(
    @ApplicationContext context: Context
) {

    private val soundPlayer = ExoPlayer.Builder(context).build().apply {
        prepare()
    }
    private val mediaItem = MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(R.raw.button_sound))

    fun play(){
        soundPlayer.setMediaItem(mediaItem)
        soundPlayer.play()
    }

    fun release() {
        soundPlayer.release()
    }
}