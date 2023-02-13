package doro.android.core.util

import java.util.*

object DateUtil {

    fun parseMinute(time: Long): Int {
        return (time - Date().time).toInt() / 1000 / 60
    }
}