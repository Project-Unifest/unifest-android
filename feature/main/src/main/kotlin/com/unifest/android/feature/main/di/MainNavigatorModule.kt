package com.unifest.android.feature.main.di

import android.app.Activity
import android.content.Intent
import com.unifest.android.core.common.extension.startActivityWithAnimation
import com.unifest.android.feature.main.MainActivity
import com.unifest.android.feature.navigator.MainNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class DefaultMainNavigator @Inject constructor() : MainNavigator {
    override fun navigateFrom(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent,
    ) {
        activity.startActivityWithAnimation<MainActivity>(
            withFinish = withFinish,
            intentBuilder = intentBuilder,
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MainNavigatorModule {
    @Singleton
    @Binds
    abstract fun bindMainNavigator(defaultMainNavigator: DefaultMainNavigator): MainNavigator
}
