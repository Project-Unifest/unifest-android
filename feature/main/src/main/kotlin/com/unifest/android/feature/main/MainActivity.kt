package com.unifest.android.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.feature.navigator.MapNavigator
import com.unifest.feature.navigator.IntroNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var introNavigator: IntroNavigator
    lateinit var mapNavigator: MapNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navigator: MainNavController = rememberMainNavController()
            UnifestTheme {
                MainScreen(
                    onNavigateToIntro = {
                        introNavigator.navigateFrom(
                            activity = this,
                            withFinish = false,
                        )
                    },
                    onNavigateToBooth = {

                    },
                    navigator = navigator,
                )
            }
        }
    }
}
