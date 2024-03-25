package com.unifest.android.feature.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.unifest.android.core.designsystem.theme.UnifestTheme
import com.unifest.feature.navigator.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {
    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
