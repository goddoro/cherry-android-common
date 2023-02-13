package doro.android.core.util

import android.app.Activity
import android.content.Intent

inline fun Activity.startActivityWithAnimation(
    withFinish: Boolean = false,
    intentBuilder: () -> Intent,
) {
    startActivity(intentBuilder())
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    if (withFinish) {
        finish()
    }
}

@Suppress("UNCHECKED_CAST")
fun HashMap<String, out Any?>.filterValueNotNull(): HashMap<String, Any> {
    return this.filter { it.value != null } as HashMap<String, Any>
}

inline fun <T> T.runIf(
    condition: Boolean,
    run: T.() -> T,
) = if (condition) {
    run()
} else {
    this
}