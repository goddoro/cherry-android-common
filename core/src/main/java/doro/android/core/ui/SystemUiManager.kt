package doro.android.core.ui

import android.app.Activity
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import javax.inject.Inject

interface SystemUiManager {
    fun show()
    fun hide()
    fun keepOnScreen()
}

class ActivitySystemUiManager @Inject constructor(
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

    override fun keepOnScreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}