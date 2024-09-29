package com.unifest.android.feature.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.unifest.android.core.common.UiText
import com.unifest.android.core.designsystem.component.UnifestScaffold
import com.unifest.android.core.designsystem.component.UnifestSnackBar
import com.unifest.android.feature.booth.navigation.boothNavGraph
import com.unifest.android.feature.home.navigation.homeNavGraph
import com.unifest.android.feature.liked_booth.navigation.likedBoothNavGraph
import com.unifest.android.feature.main.component.MainBottomBar
import com.unifest.android.feature.map.navigation.mapNavGraph
import com.unifest.android.feature.menu.navigation.menuNavGraph
import com.unifest.android.feature.stamp.navigation.stampNavGraph
import com.unifest.android.feature.waiting.navigation.waitingNavGraph
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SnackBarDuration = 1000L

@Composable
internal fun MainScreen(
    navigator: MainNavController = rememberMainNavController(),
    viewModel: MainViewModel = hiltViewModel(),
) {
    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val waitingId by viewModel.waitingId.collectAsState()
    val boothId by viewModel.boothId.collectAsState()


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

    LaunchedEffect(waitingId) {
        if (waitingId != 0L) {
            navigator.navigate(MainTab.WAITING)
            viewModel.setWaitingId(0L)
        }
    }

    LaunchedEffect(boothId) {
        if (boothId != 0L) {
            navigator.navigateToBoothDetail(viewModel.boothId.value)
            viewModel.setBoothId(0L)
        }
    }

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
        containerColor = White,
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
            boothNavGraph(
                padding = innerPadding,
                navController = navigator.navController,
                popBackStack = navigator::popBackStackIfNotMap,
                navigateToBoothLocation = navigator::navigateToBoothLocation,
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
            stampNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotMap,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
            )
            likedBoothNavGraph(
                padding = innerPadding,
                popBackStack = navigator::popBackStackIfNotMap,
                navigateToBoothDetail = navigator::navigateToBoothDetail,
                onShowSnackBar = onShowSnackBar,
            )
        }
    }
}
