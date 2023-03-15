package doro.android.core.util

import android.os.Parcelable
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket.EVENT_CONNECT_ERROR
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@OptIn(DelicateCoroutinesApi::class)
class CherrySocketClient(
    val userHolder: UserHolder,
    val onSocketConnected: () -> Unit,
    val onSocketDisconnected: () -> Unit
) {

    private val TAG = CherrySocketClient::class.java.simpleName
    private val options = IO.Options().apply {
        transports = arrayOf("websocket")
        extraHeaders = hashMapOf<String, List<String>>().apply {
            this["userId"] = listOf("${userHolder.getUserId()}")
        }
    }
    private val socket = IO.socket("http://15.165.196.152:7998", options)


    init {
        GlobalScope.launch(Dispatchers.Main) {
            listen().collect {
                try {
                    val command = JSONObject(it[0].toString())
                    val success = command.optBoolean("success")
                    val type = command.optString("type")
                    val networkCameraAddress = command.optString("cameraUrl")
                    val machineNumber = command.optString("machineNumber")
                    val streamingAddress = command.optString("streamUrl")
                    val credit = command.optInt("credit")
                    val status = command.optString("status")
                    val timer = command.optInt("timer")

//                    Log.d(TAG, "type = $type")
//                    Log.d("Socket", "networkCameraAddress = $networkCameraAddress")
//                    Log.d("Socket", "machineNumber = $machineNumber")
//                    Log.d("Socket", "streamingAddress = $streamingAddress")
//                    Log.d("Socket", "credit = $credit")
//                    Log.d("Socket", "status = $status")

                    if (!success) {
                        Broadcast.machineResponseFail.emit(Unit)
                    } else {
                        when (type) {
                            SocketMessageType.SS.name -> {
                                Broadcast.machineStatusChange.emit(
                                    MachineStatusValue(
                                        status = status,
                                        timer = timer,
                                        machineNumber = machineNumber,
                                    )
                                )
                            }
                            SocketMessageType.NC.name -> {
                                Broadcast.notifyCreditEvent.emit(credit)
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
                            SocketMessageType.ALL_BREAK_OUT.name -> {
                                Broadcast.allBreakOutEvent.emit(Unit)
                            }

                        }
                    }
                } catch (e: Throwable){
                }
            }
        }
    }

    private fun listen(): Flow<Array<out Any>> = callbackFlow {

        socket.connect()

        socket.on(io.socket.client.Socket.EVENT_CONNECT) {
            // 소켓 서버에 연결이 성공하면 호출됩니다.
            onSocketConnected()
            Log.i("Socket", "Connect")
        }.on(io.socket.client.Socket.EVENT_DISCONNECT) { args ->
            // 소켓 서버 연결이 끊어질 경우에 호출됩니다.
            onSocketDisconnected()
            Log.i("Socket", "Disconnet: ${args[0]}")
        }.on(EVENT_CONNECT_ERROR) { args ->
            socket.connect()
            Log.i("Socket", "Connect Error: ${args[0]}")
        }.on("events") {
            it.forEach { value ->
                Log.d(TAG, value.toString())
            }
            trySend(it)
        }
        awaitClose()
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
    val holdSlotNetworking = MutableStateFlow(false)
    val machineStatusChange = MutableSharedFlow<MachineStatusValue>()
    val allBreakOutEvent = MutableSharedFlow<Unit>()
    val machineResponseFail = MutableSharedFlow<Unit>()
}

enum class SocketMessageType {
    IC, OC, HS, RS, SS, NC, ALL_BREAK_OUT
}


@Parcelize
data class HoldSlotValue(
    val cameraUrl: String,
    val streamUrl: String,
    val machineNumber: String,
    val credit: Int,
    var timer: Int = 0,
): Parcelable

data class MachineStatusValue(
    val status: String,
    val timer: Int,
    val machineNumber: String,
)