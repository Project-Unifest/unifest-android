package com.unifest.android.feature.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.feature.map.MapRoute

const val MAP_ROUTE = "map_route"

fun NavController.navigateToMap(navOptions: NavOptions) {
    navigate(MAP_ROUTE, navOptions)
}

fun NavGraphBuilder.mapNavGraph(
    onShowSnackBar: (message: Int) -> Unit,
    onNavigateToBooth: (Long, (Int) -> Unit) -> Unit,
) {
    composable(route = MAP_ROUTE) {
        MapRoute(
            onShowSnackBar = onShowSnackBar,
            onNavigateToBooth = onNavigateToBooth,
        )
    }
}
