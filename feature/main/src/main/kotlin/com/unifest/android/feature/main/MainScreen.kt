package com.unifest.android.feature.main

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Consumer
import androidx.navigation.compose.NavHost
import com.unifest.android.core.common.UiText
import com.unifest.android.core.designsystem.component.UnifestScaffold
import com.unifest.android.core.designsystem.component.UnifestSnackBar
import com.unifest.android.feature.booth.navigation.boothNavGraph
import com.unifest.android.feature.booth_detail.navigation.boothDetailNavGraph
import com.unifest.android.feature.home.navigation.homeNavGraph
import com.unifest.android.feature.liked_booth.navigation.likedBoothNavGraph
import com.unifest.android.feature.main.component.MainBottomBar
import com.unifest.android.feature.map.navigation.mapNavGraph
import com.unifest.android.feature.menu.navigation.menuNavGraph
import com.unifest.android.feature.waiting.navigation.waitingNavGraph
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SnackBarDuration = 1000L

@Composable
internal fun MainScreen(
    navigator: MainNavController = rememberMainNavController(),
) {
    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val onShowSnackBar: (message: UiText) -> Unit = { message ->
        scope.launch {
            val job = launch {
                snackBarState.showSnackbar(
                    message = message.asString(context),
                    duration = SnackbarDuration.Short,
                )
            }
            delay(SnackBarDuration)
            job.cancel()
        }
    }

    HandleNewIntent(navigator = navigator)

    UnifestScaffold(
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toImmutableList(),
                currentTab = navigator.currentTab,
                onTabSelected = {
                    navigator.navigate(it)
                },
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarState,
                snackbar = {
                    UnifestSnackBar(snackBarData = it)
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            modifier = Modifier.fillMaxSize(),
        ) {
            homeNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotMap,
                onShowSnackBar = onShowSnackBar,
            )
            mapNavGraph(
                padding = innerPadding,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
                onShowSnackBar = onShowSnackBar,
            )
            boothDetailNavGraph(
                padding = innerPadding,
                navController = navigator.navController,
                popBackStack = navigator::popBackStackIfNotMap,
                navigateToBoothDetailLocation = navigator::navigateToBoothDetailLocation,
                navigateToWaiting = navigator::navigateToWaiting,
            )
            waitingNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotMap,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
            )
            menuNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotMap,
                navigateToLikedBooth = navigator::navigateToLikedBooth,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
                onShowSnackBar = onShowSnackBar,
            )
//            stampNavGraph(
//                padding = innerPadding,
//                popBackStack = navigator::popBackStackIfNotMap,
//                navigateToBoothDetail = navigator::navigateToBoothDetail,
//            )
            likedBoothNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotMap,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
                onShowSnackBar = onShowSnackBar,
            )
            boothNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotMap,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
            )
        }
    }
}

@Composable
private fun HandleNewIntent(navigator: MainNavController) {
    val activity = LocalActivity.current as ComponentActivity
    DisposableEffect(Unit) {
        val onNewIntentConsumer = Consumer<Intent> { intent ->
            Timber.d("onNewIntent -> $intent")
            Timber.d("onNewIntent -> waitingId: ${intent.getStringExtra("waitingId")}")
            Timber.d("onNewIntent -> boothId: ${intent.getStringExtra("boothId")}")

            if (intent.getStringExtra("waitingId") != null) {
                val waitingId = intent.getStringExtra("waitingId")
                Timber.d("navigate_to_waiting -> waitingId: $waitingId")
                if (waitingId != null) {
                    navigator.navigate(MainTab.WAITING)
                }
            } else if (intent.getStringExtra("boothId") != null) {
                val boothId = intent.getStringExtra("boothId")
                Timber.d("navigate_to_booth -> boothId: $boothId")
                if (boothId != null) {
                    navigator.navigateToBoothDetail(boothId.toLong())
                }
            }
        }
        activity.addOnNewIntentListener(onNewIntentConsumer)
        onDispose { activity.removeOnNewIntentListener(onNewIntentConsumer) }
    }
}
