package doro.android.core.util

import javax.inject.Inject

class UserHolder @Inject constructor(
    private val prefUtil: PrefUtil
) {
    fun setUser(userId: Int, token: String?) {
        prefUtil.setString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_ID, userId.toString())
        prefUtil.setString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_TOKEN, token)
    }

    fun getUserId(): Int {
        return Integer.parseInt(prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_ID) ?: "-1")
    }

    fun getToken(): String? {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_TOKEN)
    }

    fun hasSession(): Boolean {
        return getUserId() != -1
    }

    fun clearUser(){
        prefUtil.remove(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_ID)
    }

    fun getUserType(): String {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_TYPE).orEmpty()
    }

    fun setUserType(userType: UserType){
        prefUtil.setString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_TYPE, userType.name)
    }

    fun getEmail(): String? {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_EMAIL)
    }

    fun setUserEmail(email: String){
        prefUtil.setString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_EMAIL, email)
    }
}

enum class UserType{
    player, agent, cashier
}