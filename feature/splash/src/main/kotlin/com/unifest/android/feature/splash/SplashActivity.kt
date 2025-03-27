package com.unifest.android.feature.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.unifest.android.core.designsystem.theme.DarkGrey100
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.android.feature.navigator.IntroNavigator
import com.unifest.android.feature.navigator.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController
import timber.log.Timber
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

        val hasFcmData = intent?.extras?.let { extras ->
            extras.getString("boothId") != null ||
                extras.getString("waitingId") != null
        } ?: false

        Timber.d("Intent extras: ${intent?.extras}")
        Timber.d("hasFcmData: $hasFcmData")
        Timber.d("boothId: ${intent?.extras?.getString("boothId")}")
        Timber.d("waitingId: ${intent?.extras?.getString("waitingId")}")

        setContent {
            val systemUiController = rememberExSystemUiController()
            val isDarkTheme = isSystemInDarkTheme()

            DisposableEffect(systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = if (isDarkTheme) DarkGrey100 else Color.White,
                    darkIcons = !isDarkTheme,
                    isNavigationBarContrastEnforced = false,
                )

                onDispose {}
            }

            UnifestTheme {
                SplashRoute(
                    navigateToIntro = {
                        introNavigator.navigateFrom(
                            activity = this@SplashActivity,
                            withFinish = true,
                        )
                    },
                    navigateToMain = {
                        if (hasFcmData) {
                            // 앱이 백그라운드 상태일 때, 알림을 수신한 경우
                            mainNavigator.navigateFrom(
                                activity = this@SplashActivity,
                                withFinish = true,
                            ) {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP
                                intent.extras?.let { putExtras(it) }
                                this
                            }
                        } else {
                            mainNavigator.navigateFrom(
                                activity = this@SplashActivity,
                                withFinish = true,
                            )
                        }
                    },
                )
            }
        }
    }
}
