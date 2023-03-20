package doro.cherry.rtsp.player.codec

import android.util.Log
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

class FrameQueue(val type: String, val frameQueueSize: Int, val onFrameQueueError: (String) -> Unit = {}) {

    data class Frame (
        val data: ByteArray,
        val offset: Int,
        val length: Int,
        val timestamp: Long
    )

    private val queue: BlockingQueue<Frame> = ArrayBlockingQueue(frameQueueSize)
    private var errorOccurred = false

    @Throws(InterruptedException::class)
    fun push(frame: Frame): Boolean {
        if (queue.offer(frame, 5, TimeUnit.MILLISECONDS)) {
            return true
        }
        if (!errorOccurred) {
            onFrameQueueError("$type can not push, queue is full")
            errorOccurred = true
        }
        Log.w(TAG, "Cannot add frame, queue is full")
        return false
    }

    @Throws(InterruptedException::class)
    fun pop(): Frame? {
        try {
            val frame: Frame? = queue.poll(100, TimeUnit.MILLISECONDS)
            if (frame == null) {
                Log.w(TAG, "Cannot get frame, queue is empty")
            }
            return frame
        } catch (e: InterruptedException) {
            Log.w(TAG, "Cannot add frame, queue is full", e)
            if (!errorOccurred) {
                onFrameQueueError("$type can not pop, frame is null in Interrupt!")
                errorOccurred = true
            }
            Thread.currentThread().interrupt()
        }
        return null
    }

    fun clear() {
        queue.clear()
    }

    fun getQueueSize() = queue.size

    companion object {
        private val TAG: String = FrameQueue::class.java.simpleName
    }

}
