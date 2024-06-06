package com.unifest.android.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import com.unifest.android.core.designsystem.theme.DarkBackground
import com.unifest.android.core.designsystem.theme.LightBackground
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.feature.navigator.IntroNavigator
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var introNavigator: IntroNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navigator: MainNavController = rememberMainNavController()
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
                MainScreen(
                    navigator = navigator,
                )
            }
        }
    }
}
