package com.unifest.android.feature.stamp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.feature.stamp.StampRoute

// const val STAMP_ROUTE = "stamp_route"

fun NavController.navigateToStamp(navOptions: NavOptions) {
    navigate(MainTabRoute.Stamp, navOptions)
    // navigate(STAMP_ROUTE, navOptions)
}

fun NavGraphBuilder.stampNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
) {
    composable<MainTabRoute.Stamp> {
    // composable(route = STAMP_ROUTE) {
        StampRoute(
            padding = padding,
            popBackStack = popBackStack,
            navigateToBoothDetail = navigateToBoothDetail,
        )
    }
}
