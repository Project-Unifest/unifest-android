package com.unifest.android.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.unifest.android.core.designsystem.theme.DarkBackground
import com.unifest.android.core.designsystem.theme.LightBackground
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.feature.navigator.IntroNavigator
import com.unifest.feature.navigator.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    @Inject
    lateinit var introNavigator: IntroNavigator

    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberExSystemUiController()
            val isDarkTheme = isSystemInDarkTheme()

            DisposableEffect(systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = if (isDarkTheme) DarkBackground else LightBackground,
                    darkIcons = !isDarkTheme,
                    isNavigationBarContrastEnforced = false,
                )

                onDispose {}
            }

            UnifestTheme {
                SplashRoute(
                    navigateToIntro = {
                        introNavigator.navigateFrom(
                            activity = this,
                            withFinish = true,
                        )
                    },
                    navigateToMain = {
                        mainNavigator.navigateFrom(
                            activity = this,
                            withFinish = true,
                        )
                    },
                )
            }
        }
    }
}
