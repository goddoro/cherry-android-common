package doro.android.core.util

import java.util.*

object DateUtil {

    fun parseMinute(time: Int): Int {
        return (time - Date().time.toInt()) / 1000 / 60
    }
}