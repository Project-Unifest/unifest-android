package com.unifest.android.feature.booth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.unifest.android.core.common.extension.sharedViewModel
import com.unifest.android.core.navigation.Route
import com.unifest.android.feature.booth.BoothDetailRoute
import com.unifest.android.feature.booth.BoothLocationRoute
import com.unifest.android.feature.booth.viewmodel.BoothViewModel

fun NavController.navigateToBoothDetail(
    boothId: Long,
) {
    navigate(Route.Booth.BoothDetail(boothId))
}

fun NavController.navigateToBoothLocation() {
    navigate(Route.Booth.BoothLocation)
}

fun NavGraphBuilder.boothNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToBoothLocation: () -> Unit,
) {
    navigation<Route.Booth>(
        startDestination = Route.Booth.BoothDetail::class,
    ) {
        composable<Route.Booth.BoothDetail> { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<BoothViewModel>(navController)
            BoothDetailRoute(
                padding = padding,
                onBackClick = popBackStack,
                navigateToBoothLocation = navigateToBoothLocation,
                viewModel = viewModel,
            )
        }
        composable<Route.Booth.BoothLocation> { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<BoothViewModel>(navController)
            BoothLocationRoute(
                onBackClick = popBackStack,
                viewModel = viewModel,
            )
        }
    }
}
