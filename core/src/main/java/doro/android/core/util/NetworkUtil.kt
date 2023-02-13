package doro.android.core.util

import android.util.Log
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*

object NetworkUtil {
    fun getIpAddress(): String {
        //Device에 있는 모든 네트워크에 대해 뺑뺑이를 돕니다.
        //Device에 있는 모든 네트워크에 대해 뺑뺑이를 돕니다.

        val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
        while (en.hasMoreElements()) {
            val networkInterface: NetworkInterface = en.nextElement()

            //네트워크 중에서 IP가 할당된 넘들에 대해서 뺑뺑이를 한 번 더 돕니다.
            val enumIpAddress: Enumeration<InetAddress> = networkInterface.inetAddresses
            while (enumIpAddress.hasMoreElements()) {
                val inetAddress: InetAddress = enumIpAddress.nextElement()

                //네트워크에는 항상 Localhost 즉, 루프백(LoopBack)주소가 있으며, 우리가 원하는 것이 아닙니다.
                //IP는 IPv6와 IPv4가 있습니다.
                //IPv6의 형태 : fe80::64b9::c8dd:7003
                //IPv4의 형태 : 123.234.123.123
                //어떻게 나오는지는 찍어보세요.
                if (inetAddress.isLoopbackAddress) {
                    Log.i(
                        "IPAddress",
                        networkInterface.displayName
                            .toString() + "(loopback) | " + inetAddress.hostAddress
                    )
                } else {
                    Log.i(
                        "IPAddress",
                        networkInterface.displayName.toString() + " | " + inetAddress.hostAddress
                    )
                }

                //루프백이 아니고, IPv4가 맞다면 리턴~~~
                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                    return inetAddress.getHostAddress().orEmpty()
                }
            }
        }
        return ""
    }
}