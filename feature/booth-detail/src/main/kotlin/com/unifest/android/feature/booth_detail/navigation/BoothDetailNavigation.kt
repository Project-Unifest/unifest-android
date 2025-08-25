package com.unifest.android.feature.booth_detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.unifest.android.core.navigation.Route
import com.unifest.android.feature.booth_detail.BoothDetailRoute
import com.unifest.android.feature.booth_detail.BoothDetailLocationRoute
import com.unifest.android.feature.booth_detail.viewmodel.BoothDetailViewModel

fun NavController.navigateToBoothDetail(
    boothId: Long,
) {
    navigate(Route.BoothDetail.Detail(boothId))
}

fun NavController.navigateToBoothDetailLocation() {
    navigate(Route.BoothDetail.Location)
}

fun NavGraphBuilder.boothDetailNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetailLocation: () -> Unit,
    navigateToWaiting: () -> Unit,
    getBackStackViewModel: @Composable (NavBackStackEntry) -> BoothDetailViewModel,
) {
    navigation<Route.BoothDetail>(
        startDestination = Route.BoothDetail.Detail::class,
    ) {
        composable<Route.BoothDetail.Detail> { navBackStackEntry ->
            BoothDetailRoute(
                padding = padding,
                popBackStack = popBackStack,
                navigateToBoothDetailLocation = navigateToBoothDetailLocation,
                navigateToWaiting = navigateToWaiting,
                viewModel = getBackStackViewModel(navBackStackEntry),
            )
        }
        composable<Route.BoothDetail.Location> { navBackStackEntry ->
            BoothDetailLocationRoute(
                popBackStack = popBackStack,
                viewModel = getBackStackViewModel(navBackStackEntry),
            )
        }
    }
}
