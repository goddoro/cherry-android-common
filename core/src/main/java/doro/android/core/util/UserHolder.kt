package doro.android.core.util

import javax.inject.Inject

class UserHolder @Inject constructor(
    private val prefUtil: PrefUtil
) {
    fun setUser(userId: Int, token: String?) {
        prefUtil.setString(
            PrefKeys.Session.FILE_NAME,
            PrefKeys.Session.KEY_USER_ID,
            userId.toString()
        )
        prefUtil.setString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_TOKEN, token)
    }

    fun getUserId(): Int {
        return Integer.parseInt(
            prefUtil.getString(
                PrefKeys.Session.FILE_NAME,
                PrefKeys.Session.KEY_USER_ID
            ) ?: "-1"
        )
    }

    fun getToken(): String? {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_TOKEN)
    }

    fun hasSession(): Boolean {
        return getUserId() != -1
    }

    fun clearUser() {
        prefUtil.remove(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_ID)
        prefUtil.remove(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_TOKEN)
        prefUtil.remove(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_EMAIL)
    }

    fun getUserType(): String {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_TYPE)
            .orEmpty()
    }

    fun setUserType(userType: UserType) {
        prefUtil.setString(
            PrefKeys.Session.FILE_NAME,
            PrefKeys.Session.KEY_USER_TYPE,
            userType.name
        )
    }

    fun getEmail(): String? {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_EMAIL)
    }

    fun setUserEmail(email: String) {
        prefUtil.setString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_USER_EMAIL, email)
    }

    fun getCurrentServerEndPoint(): String {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_SERVER_END_POINT)
            ?: EndPoint.ASV_PRODUCTION_URL.url
    }

    fun getCurrentServerSocketUrl(): String {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_SOCKET_URL)
            ?: EndPoint.ASV_PRODUCTION_URL.socketUrl
    }

    fun getCurrentServerType(): String {
        return prefUtil.getString(PrefKeys.Session.FILE_NAME, PrefKeys.Session.KEY_SERVER_TYPE)
            ?: EndPoint.ASV_PRODUCTION_URL.nickName
    }

    fun setCurrentServerEndPoint(endPoint: EndPoint) {
        prefUtil.setString(
            PrefKeys.Session.FILE_NAME,
            PrefKeys.Session.KEY_SERVER_END_POINT,
            endPoint.url
        )
        prefUtil.setString(
            PrefKeys.Session.FILE_NAME,
            PrefKeys.Session.KEY_SERVER_TYPE,
            endPoint.nickName
        )
        prefUtil.setString(
            PrefKeys.Session.FILE_NAME,
            PrefKeys.Session.KEY_SOCKET_URL,
            endPoint.socketUrl
        )
    }
}

enum class UserType {
    player, agent, cashier
}