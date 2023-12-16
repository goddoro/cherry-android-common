package doro.android.core.util

import androidx.compose.ui.unit.dp

object Const {

    val TOP_APP_BAR_HEIGHT = 56.dp

    const val HELP_BUTTON = 1000

    const val MACHINE_GAME_EXTRA = "MACHINE_GAME_EXTRA"

    const val PASSWORD_MIN_LENGTH = 6

    const val USERNAME_MIN_LENGTH = 4
}

enum class EndPoint(val url: String, val id: Int, val nickName: String, val type: String, val socketUrl: String = "") {

    ASV_DEVELOPER_URL("http://15.165.196.152:3000/", 0, "APPLE", "Develop(EC2)", "http://15.165.196.152:7998/" ),
    ASV_TEST_URL("http://61.72.138.120:9300", 4, "BANANA", "Test(Physical Server)", "http://61.72.138.120:9998/"),
    ASV_STAGING_URL("http://13.251.118.100:3000/", 1, "CARROT", "Staging(EC2)", "http://13.251.118.100:7998/"),
    ASV_PRODUCTION_URL("http://asv.cherrysnc.com:3000/", 2, "DURIAN", "Production", "http://47.128.147.160:7998"),

    LOG_SERVER_URL("http://13.124.181.184:3000/", 3, "EGG", "Log"),
}
