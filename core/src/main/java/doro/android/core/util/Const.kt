package doro.android.core.util

import androidx.compose.ui.unit.dp

object Const {

    val TOP_APP_BAR_HEIGHT = 56.dp

    const val HELP_BUTTON = 1000

    const val MACHINE_GAME_EXTRA = "MACHINE_GAME_EXTRA"

    const val PASSWORD_MIN_LENGTH = 10

    const val USERNAME_MIN_LENGTH = 4
}

enum class EndPoint(val url: String, val id: Int, val type: String) {

    ASV_DEVELOPER_URL("http://15.165.196.152:3000/", 0, "Develop"),
    ASV_TEST_URL("http://15.165.196.152:3000/", 4,"Test"),
    ASV_STAGING_URL("http://15.165.196.152:3000/", 1,"Staging"),
    ASV_PRODUCTION_URL("http://15.165.196.152:3000/", 2,"Production"),
    LOG_SERVER_URL("http://13.124.181.184:3000/", 3,"Log")
}