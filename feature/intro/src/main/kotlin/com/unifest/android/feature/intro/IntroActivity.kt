package com.unifest.android.feature.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.feature.navigator.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {
    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberExSystemUiController()

            DisposableEffect(systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = Color.White,
                    darkIcons = true,
                    isNavigationBarContrastEnforced = false,
                )

                onDispose {}
            }

            UnifestTheme {
                IntroRoute(
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
