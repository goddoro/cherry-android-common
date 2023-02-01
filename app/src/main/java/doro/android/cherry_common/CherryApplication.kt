package doro.android.cherry_common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CherryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}
