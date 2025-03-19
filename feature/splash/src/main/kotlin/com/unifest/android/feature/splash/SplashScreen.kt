package com.unifest.android.feature.splash

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.unifest.android.core.common.ObserveAsEvents
import com.unifest.android.core.common.extension.findActivity
import com.unifest.android.core.data.BuildConfig
import com.unifest.android.core.designsystem.component.AppUpdateDialog
import com.unifest.android.core.designsystem.component.LoadingWheel
import com.unifest.android.feature.splash.viewmodel.SplashUiAction
import com.unifest.android.feature.splash.viewmodel.SplashUiEvent
import com.unifest.android.feature.splash.viewmodel.SplashUiState
import com.unifest.android.feature.splash.viewmodel.SplashViewModel
import androidx.core.net.toUri

@Composable
internal fun SplashRoute(
    navigateToMain: () -> Unit,
    navigateToIntro: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val shouldUpdate by viewModel.shouldUpdate.collectAsStateWithLifecycle(null)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context.findActivity()

    LaunchedEffect(key1 = shouldUpdate) {
        if (shouldUpdate == false) {
            viewModel.checkIntroCompletion()
            viewModel.refreshFCMToken()
        }
    }

    ObserveAsEvents(flow = viewModel.uiEvent) { event ->
        when (event) {
            is SplashUiEvent.NavigateToIntro -> {
                navigateToIntro()
            }

            is SplashUiEvent.NavigateToMain -> {
                navigateToMain()
            }

            is SplashUiEvent.NavigateToPlayStore -> {
                val playStoreUrl = "http://play.google.com/store/apps/details?id=${BuildConfig.PACKAGE_NAME}"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = playStoreUrl.toUri()
                    setPackage("com.android.vending")
                }
                startActivity(context, intent, null)
            }

            is SplashUiEvent.CloseApp -> {
                activity.finish()
            }
        }
    }

    SplashScreen(
        shouldUpdate = shouldUpdate,
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
fun SplashScreen(
    shouldUpdate: Boolean?,
    uiState: SplashUiState,
    onAction: (SplashUiAction) -> Unit,
) {
    if (uiState.isLoading) {
        LoadingWheel(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        )
    }
    if (shouldUpdate == true) {
        AppUpdateDialog(
            onDismissRequest = { onAction(SplashUiAction.OnUpdateDismissClick) },
            onUpdateClick = { onAction(SplashUiAction.OnUpdateClick) },
        )
    }

    if (uiState.isLoading) {
        LoadingWheel(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        )
    }
}
