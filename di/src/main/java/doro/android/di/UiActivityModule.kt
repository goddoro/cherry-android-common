package doro.android.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import doro.android.core.ui.ActivitySystemUiManager
import doro.android.core.ui.SystemUiManager

@Module
@InstallIn(ActivityComponent::class)
object UiActivityModule {

    @Provides
    @ActivityScoped
    fun provideSystemUiManager(
        activity: Activity
    ): SystemUiManager {
        return ActivitySystemUiManager(activity)
    }


}