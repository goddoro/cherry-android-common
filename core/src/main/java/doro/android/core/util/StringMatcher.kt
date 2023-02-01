package doro.android.core.util

import android.util.Patterns
import java.util.regex.Matcher

object StringMatcher {

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= Const.PASSWORD_MIN_LENGTH
    }

    fun isValidUsername(username: String): Boolean {
        return username.length >= Const.USERNAME_MIN_LENGTH
    }
}