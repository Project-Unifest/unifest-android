package com.unifest.android.feature.booth_detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.unifest.android.core.common.extension.sharedViewModel
import com.unifest.android.core.navigation.Route
import com.unifest.android.feature.booth_detail.BoothDetailRoute
import com.unifest.android.feature.booth_detail.BoothDetailLocationRoute
import com.unifest.android.feature.booth_detail.viewmodel.BoothViewModel

fun NavController.navigateToBoothDetail(
    boothId: Long,
) {
    navigate(Route.BoothDetail.BoothDetail(boothId))
}

fun NavController.navigateToBoothDetailLocation() {
    navigate(Route.BoothDetail.BoothLocation)
}

fun NavGraphBuilder.boothDetailNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToBoothDetailLocation: () -> Unit,
    navigateToWaiting: () -> Unit,
) {
    navigation<Route.BoothDetail>(
        startDestination = Route.BoothDetail.BoothDetail::class,
    ) {
        composable<Route.BoothDetail.BoothDetail> { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<BoothViewModel>(navController)
            BoothDetailRoute(
                padding = padding,
                popBackStack = popBackStack,
                navigateToBoothDetailLocation = navigateToBoothDetailLocation,
                navigateToWaiting = navigateToWaiting,
                viewModel = viewModel,
            )
        }
        composable<Route.BoothDetail.BoothLocation> { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<BoothViewModel>(navController)
            BoothDetailLocationRoute(
                popBackStack = popBackStack,
                viewModel = viewModel,
            )
        }
    }
}
