package doro.android.core.util

import android.os.Parcelable
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket.EVENT_CONNECT_ERROR
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@OptIn(DelicateCoroutinesApi::class)
class CherrySocketClient(
    val userHolder: UserHolder
) {

    private val TAG = CherrySocketClient::class.java.simpleName
    private val options = IO.Options().apply {
        transports = arrayOf("websocket")
        extraHeaders = hashMapOf<String, List<String>>().apply {
            this["userId"] = listOf("${userHolder.getUserId()}")
        }
    }
//    private val socket = IO.socket("http://15.165.196.152:7998", options)
    private val socket = IO.socket("http://221.140.196.19:7998", options)

    init {


        GlobalScope.launch(Dispatchers.IO) {
            socket.connect()

            socket.on(io.socket.client.Socket.EVENT_CONNECT) {
                // 소켓 서버에 연결이 성공하면 호출됩니다.
                Log.i("Socket", "Connect")
            }.on(io.socket.client.Socket.EVENT_DISCONNECT) { args ->
                // 소켓 서버 연결이 끊어질 경우에 호출됩니다.
                Log.i("Socket", "Disconnet: ${args[0]}")
            }.on(EVENT_CONNECT_ERROR) { args ->
                socket.connect()
                Log.i("Socket", "Connect Error: ${args[0]}")
            }.on("events") {
                Log.i("Socket", it.toString())

                val type = "good"
                val credit = 3
                val networkCameraAddress = "zxcv"
                val machineNumber = "01001"
                val streamingAddress = "zxcv"

                GlobalScope.launch {
                    when (type) {
                        SocketMessageType.SS.name -> {
                            Broadcast.refreshMachineEvent.emit(Unit)
                        }
                        SocketMessageType.NC.name -> {
                            Broadcast.notifyCreditEvent.emit(credit)
                        }
                        SocketMessageType.HS.name -> {
                            Broadcast.holdSlotNetworking.emit(false)
                            Broadcast.holdSlotEvent.emit(
                                HoldSlotValue(
                                    cameraUrl = networkCameraAddress,
                                    streamUrl = streamingAddress,
                                    machineNumber = machineNumber,
                                    credit = credit,
                                )
                            )
                        }
                        SocketMessageType.RS.name -> {
                            Broadcast.releaseSlotEvent.emit(credit)
                        }
                        SocketMessageType.IC.name -> {
                            Broadcast.creditInEvent.emit(credit)
                        }
                        SocketMessageType.OC.name -> {
                            Broadcast.creditOutEvent.emit(credit)
                        }
                    }
                }
            }
        }
    }
}

object Broadcast {
    val notificationRefreshEvent = MutableSharedFlow<Unit>()
    val notifyCreditEvent = MutableSharedFlow<Int>()
    val releaseSlotEvent = MutableSharedFlow<Int>()
    val holdSlotEvent = MutableSharedFlow<HoldSlotValue>()
    val creditInEvent = MutableSharedFlow<Int>()
    val creditOutEvent = MutableSharedFlow<Int>()
    val userRefreshEvent = MutableSharedFlow<Unit>()
    val holdSlotNetworking = MutableSharedFlow<Boolean>()
    val refreshMachineEvent = MutableSharedFlow<Unit>()
    val machineStatusChange = MutableSharedFlow<String>()
}

enum class SocketMessageType {
    IC, OC, HS, RS, SS, NC
}



@Parcelize
data class HoldSlotValue(
    val cameraUrl: String,
    val streamUrl: String,
    val machineNumber: String,
    val credit: Int,
) : Parcelable