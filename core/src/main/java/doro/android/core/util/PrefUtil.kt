package doro.android.core.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PrefUtil @Inject constructor(
    val context: Context
) {

    private fun getSharedPreferences(
        fileName: String?,
        mode: Int = Context.MODE_PRIVATE
    ): SharedPreferences {
        return context.getSharedPreferences(fileName, mode)
    }

    fun setString(fileName: String?, key: String?, value: String?): Boolean {
        val pref = getSharedPreferences(fileName)
        val editor = pref.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun getString(fileName: String?, key: String?): String? {
        val pref = getSharedPreferences(fileName)
        return pref.getString(key, null)
    }

    fun remove(fileName: String?, key: String?): Boolean {
        val pref = getSharedPreferences(fileName)
        return pref.edit().remove(key).commit()
    }
}