package doro.android.core.ui

import android.app.Activity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

interface SystemUiManager {
    fun show()
    fun hide()
}

class ActivitySystemUiManager(
    activity: Activity
) : SystemUiManager {

    private val window = activity.window

    private val windowInsetsController =
        WindowCompat.getInsetsController(activity.window, activity.window.decorView)

    init {
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    override fun show() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }

    override fun hide() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
}