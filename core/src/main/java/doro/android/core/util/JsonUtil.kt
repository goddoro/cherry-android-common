package doro.android.core.util

import org.json.JSONArray
import org.json.JSONObject


fun JSONObject.toMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    val keysItr: Iterator<String> = this.keys()
    while (keysItr.hasNext()) {
        val key = keysItr.next()
        var value: Any = this.get(key)
        when (value) {
            is JSONArray -> value = value.toIntList()
            is JSONObject -> value = value.toMap()
        }
        map[key] = value
    }
    return map
}

fun JSONArray.toIntList(): List<Int> {
    val list = mutableListOf<Int>()
    for (i in 0 until this.length()) {
        var value: Any = this[i]
        when (value) {
            is JSONArray -> value = value.toIntList()
            is JSONObject -> value = value.toMap()
        }
        list.add(Integer.parseInt(value.toString()))
    }
    return list
}