package doro.cherry.rtsp.player.codec

import android.media.MediaCodec
import android.media.MediaCodec.OnFrameRenderedListener
import android.media.MediaFormat
import android.util.Log
import android.view.Surface
import com.google.android.exoplayer2.util.Util
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class VideoDecodeThread(
    private val surface: Surface,
    val mimeType: String,
    private val width: Int,
    private val height: Int,
    private val videoFrameQueue: FrameQueue,
    private val onFrameRenderedListener: OnFrameRenderedListener,
    val onVideoDecodeError: (String) -> Unit = {}
) : Thread() {

    private var exitFlag: AtomicBoolean = AtomicBoolean(false)
    private var errorInvoked: Boolean = false
    var currentFrame: FrameQueue.Frame? = null
    var dequeueInputBufferCount = 0
    var dequeueOutputBufferCount = 0
    var releaseOutputBufferCount = 0

    var infoOutputFormatChanged = 0
    var infoTryAgainLater = 0
    var outIndexNegative = 0
    var onFrameRenderTime = 0L
    val decoder = MediaCodec.createDecoderByType(mimeType)

    fun stopAsync() {
        if (DEBUG) Log.v(TAG, "stopAsync()")
        exitFlag.set(true)
        // Wake up sleep() code
        interrupt()
    }

    private fun getDecoderSafeWidthHeight(decoder: MediaCodec): Pair<Int, Int> {
        val capabilities = decoder.codecInfo.getCapabilitiesForType(mimeType).videoCapabilities
        return if (capabilities.isSizeSupported(width, height)) {
            Pair(width, height)
        } else {
            val widthAlignment = capabilities.widthAlignment
            val heightAlignment = capabilities.heightAlignment
            Pair(
                Util.ceilDivide(width, widthAlignment) * widthAlignment,
                Util.ceilDivide(height, heightAlignment) * heightAlignment
            )
        }
    }

    override fun run() {
        if (DEBUG) Log.d(TAG, "$name started")

        try {
            decoder.reset()
            val widthHeight = getDecoderSafeWidthHeight(decoder)
            val format =
                MediaFormat.createVideoFormat(mimeType, widthHeight.first, widthHeight.second)
            if (DEBUG) Log.d(
                TAG,
                "Configuring surface ${widthHeight.first}x${widthHeight.second} w/ '$mimeType', max instances: ${
                decoder.codecInfo.getCapabilitiesForType(mimeType).maxSupportedInstances
                }"
            )
            decoder.setOnFrameRenderedListener(onFrameRenderedListener, null)
            decoder.configure(format, surface, null, 0)

            // TODO: add scale option (ie: FIT, SCALE_CROP, SCALE_NO_CROP)
            // decoder.setVideoScalingMode(MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)

            decoder.start()

            val bufferInfo = MediaCodec.BufferInfo()

            // Main loop
            while (!exitFlag.get()) {
                val inIndex: Int = decoder.dequeueInputBuffer(DEQUEUE_INPUT_TIMEOUT_US)
                if (inIndex >= 0) {
                    // fill inputBuffers[inputBufferIndex] with valid data
                    val byteBuffer: ByteBuffer? = decoder.getInputBuffer(inIndex)
                    byteBuffer?.rewind()

                    val frame = videoFrameQueue.pop()
                    currentFrame = frame
                    if (frame == null) {
                        if (DEBUG) Log.d(TAG, "Empty video frame")
                        // Release input buffer
                        decoder.queueInputBuffer(inIndex, 0, 0, 0L, 0)
                    } else {
                        byteBuffer?.put(frame.data, frame.offset, frame.length)
                        decoder.queueInputBuffer(
                            inIndex,
                            frame.offset,
                            frame.length,
                            frame.timestamp,
                            0
                        )
                    }
                    dequeueInputBufferCount = frame?.length ?: 0
                }
                if (exitFlag.get()) break
                val outIndex = decoder.dequeueOutputBuffer(bufferInfo, DEQUEUE_OUTPUT_BUFFER_TIMEOUT_US)
                dequeueOutputBufferCount = bufferInfo.size
                when (outIndex) {
                    MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                        Log.d(TAG, "Decoder format changed: ${decoder.outputFormat}")
                        infoOutputFormatChanged++
                    }
                    MediaCodec.INFO_TRY_AGAIN_LATER -> {
                        if (DEBUG) Log.d(
                            TAG,
                            "No output from decoder available"
                        )
                        infoTryAgainLater++
                    }
                    else -> {
                        if (outIndex >= 0) {
                            if (DEBUG) Log.d(TAG, "release output buffer ${bufferInfo.size}")
                            decoder.releaseOutputBuffer(
                                outIndex,
                                bufferInfo.size != 0 && !exitFlag.get()
                            )
                            dequeueOutputBufferCount = 0
                            releaseOutputBufferCount = bufferInfo.size
                        } else {
                            if (DEBUG) Log.d(TAG, "out index is negative value")
                            outIndexNegative++
                        }
                    }
                }

                // All decoded frames have been rendered, we can stop playing now
                if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                    if (DEBUG) Log.d(TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM")
                    break
                }
            }

            // Drain decoder
            val inIndex: Int = decoder.dequeueInputBuffer(DEQUEUE_INPUT_TIMEOUT_US)
            if (inIndex >= 0) {
                decoder.queueInputBuffer(inIndex, 0, 0, 0L, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
            } else {
                Log.w(TAG, "Not able to signal end of stream")
            }

            decoder.stop()
            decoder.release()
            videoFrameQueue.clear()
        } catch (e: Exception) {
            Log.e(TAG, "$name stopped due to '${e.message}'")
            if (!errorInvoked) {
                onVideoDecodeError("$name stopped due to '${e.message}'")
                errorInvoked = true
            }
            // While configuring stopAsync can be called and surface released. Just exit.
            if (!exitFlag.get()) e.printStackTrace()
            return
        }

        if (DEBUG) Log.d(TAG, "$name stopped")
    }

    companion object {
        private val TAG: String = VideoDecodeThread::class.java.simpleName
        private const val DEBUG = false

        private val DEQUEUE_INPUT_TIMEOUT_US = TimeUnit.MILLISECONDS.toMicros(500)
        private val DEQUEUE_OUTPUT_BUFFER_TIMEOUT_US = TimeUnit.MILLISECONDS.toMicros(100)
    }
}
