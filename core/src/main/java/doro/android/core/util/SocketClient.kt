package doro.android.core.util

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.net.Socket

@OptIn(DelicateCoroutinesApi::class)
class SocketClient {

    private val TAG = SocketClient::class.java.simpleName
    private val address = InetSocketAddress("221.140.196.19", 4001)

    init {


        GlobalScope.launch(Dispatchers.IO) {
            val socket = Socket()
            socket.connect(address)
        }



    }



}