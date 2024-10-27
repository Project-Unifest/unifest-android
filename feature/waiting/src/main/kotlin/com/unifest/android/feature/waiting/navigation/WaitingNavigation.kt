package com.unifest.android.feature.waiting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.feature.waiting.WaitingRoute

fun NavController.navigateToWaiting(navOptions: NavOptions) {
    navigate(MainTabRoute.Waiting, navOptions)
}

fun NavGraphBuilder.waitingNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
) {
    composable<MainTabRoute.Waiting> {
        WaitingRoute(
            padding = padding,
            popBackStack = popBackStack,
            navigateToBoothDetail = navigateToBoothDetail,
        )
    }
}
