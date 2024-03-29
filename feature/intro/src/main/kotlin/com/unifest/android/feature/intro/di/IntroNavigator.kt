package com.unifest.android.feature.intro.di

import android.app.Activity
import android.content.Intent
import com.unifest.android.core.common.extension.startActivityWithAnimation
import com.unifest.android.feature.intro.IntroActivity
import com.unifest.feature.navigator.IntroNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class IntroNavigatorImpl @Inject constructor() : IntroNavigator {
    override fun navigateFrom(
        activity: Activity,
        withFinish: Boolean,
        intentBuilder: Intent.() -> Intent,
    ) {
        activity.startActivityWithAnimation<IntroActivity>(
            withFinish = withFinish,
            intentBuilder = intentBuilder,
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class IntroNavigatorModule {
    @Singleton
    @Binds
    abstract fun bindIntroNavigator(mainNavigatorImpl: IntroNavigatorImpl): IntroNavigator
}
