package com.unifest.android.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.unifest.android.core.designsystem.theme.DarkGrey100
import com.unifest.android.core.designsystem.theme.UnifestTheme
import dagger.hilt.android.AndroidEntryPoint
import tech.thdev.compose.exteions.system.ui.controller.rememberExSystemUiController
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navigator: MainNavController = rememberMainNavController()
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
                MainScreen(
                    navigator = navigator,
                    viewModel = viewModel,
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        if (intent.getBooleanExtra("navigate_to_waiting", false)) {
            val waitingId = intent.getStringExtra("waitingId")
            Timber.d("navigate_to_waiting -> waitingId: $waitingId")
            if (waitingId != null) {
                viewModel.setWaitingId(waitingId.toLong())
            }
        } else if (intent.getBooleanExtra("navigate_to_booth", false)) {
            val boothId = intent.getStringExtra("boothId")
            Timber.d("navigate_to_booth -> boothId: $boothId")
            if (boothId != null) {
                viewModel.setBoothId(boothId.toLong())
            }
        }
    }
}
