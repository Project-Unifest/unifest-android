package com.unifest.android.feature.booth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.unifest.android.core.common.extension.sharedViewModel
import com.unifest.android.feature.booth.BoothLocationRoute
import com.unifest.android.feature.booth.BoothDetailRoute
import com.unifest.android.feature.booth.viewmodel.BoothViewModel

const val BOOTH_ID = "booth_id"
const val BOOTH_ROUTE = "booth_route/{$BOOTH_ID}"
const val BOOTH_DETAIL_ROUTE = "booth_detail_route"
const val BOOTH_LOCATION_ROUTE = "booth_location_route"

@Suppress("unused")
fun NavController.navigateToBoothDetail(
    boothId: Long,
) {
    navigate("booth_route/$boothId")
}

fun NavController.navigateToBoothLocation() {
    navigate(BOOTH_LOCATION_ROUTE)
}

fun NavGraphBuilder.boothNavGraph(
    navController: NavHostController,
    onBackClick: () -> Unit,
    onNavigateToBoothLocation: () -> Unit,
    onShowSnackBar: (Int) -> Unit,
) {
    navigation(
        startDestination = BOOTH_DETAIL_ROUTE,
        route = BOOTH_ROUTE,
        arguments = listOf(
            navArgument(BOOTH_ID) {
                type = NavType.LongType
            },
        ),
    ) {
        composable(route = BOOTH_DETAIL_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<BoothViewModel>(navController)
            BoothDetailRoute(
                onBackClick = onBackClick,
                onNavigateToBoothLocation = onNavigateToBoothLocation,
                onShowSnackBar = onShowSnackBar,
                viewModel = viewModel,
            )
        }
        composable(route = BOOTH_LOCATION_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<BoothViewModel>(navController)
            BoothLocationRoute(
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }
    }
}
