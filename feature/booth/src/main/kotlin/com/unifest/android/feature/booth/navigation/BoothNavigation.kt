package com.unifest.android.feature.booth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.unifest.android.core.common.extension.sharedViewModel
import com.unifest.android.feature.booth.BoothDetailRoute
import com.unifest.android.feature.booth.BoothLocationRoute
import com.unifest.android.feature.booth.viewmodel.BoothViewModel

const val BOOTH_ID = "booth_id"
const val BOOTH_ROUTE = "booth_route/{$BOOTH_ID}"
const val BOOTH_DETAIL_ROUTE = "booth_detail_route"
const val BOOTH_LOCATION_ROUTE = "booth_location_route"

fun NavController.navigateToBoothDetail(
    boothId: Long,
) {
    // navigate(Route.Booth.BoothDetail(boothId))
    navigate("booth_route/$boothId")
}

fun NavController.navigateToBoothLocation() {
    // navigate(Route.Booth.BoothLocation)
    navigate(BOOTH_LOCATION_ROUTE)
}

fun NavGraphBuilder.boothNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    popBackStack: () -> Unit,
    navigateToBoothLocation: () -> Unit,
) {
//    navigation<Route.Booth>(
//        startDestination = Route.Booth.BoothDetail::class,
//    ) {
    navigation(
        startDestination = BOOTH_DETAIL_ROUTE,
        route = BOOTH_ROUTE,
        arguments = listOf(
            navArgument(BOOTH_ID) {
                type = NavType.LongType
            },
        ),
    ) {
        /// composable<Route.Booth.BoothDetail> { navBackStackEntry ->
        composable(route = BOOTH_DETAIL_ROUTE) { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<BoothViewModel>(navController)
            BoothDetailRoute(
                padding = padding,
                onBackClick = popBackStack,
                navigateToBoothLocation = navigateToBoothLocation,
                viewModel = viewModel,
            )
        }
        // composable<Route.Booth.BoothLocation> { navBackStackEntry ->
        composable(route = BOOTH_LOCATION_ROUTE) { navBackStackEntry ->
            val viewModel = navBackStackEntry.sharedViewModel<BoothViewModel>(navController)
            BoothLocationRoute(
                onBackClick = popBackStack,
                viewModel = viewModel,
            )
        }
    }
}
