package com.unifest.android.feature.booth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.feature.booth.BoothRoute

fun NavController.navigateToBooth(navOptions: NavOptions) {
    navigate(MainTabRoute.Booth, navOptions)
}

fun NavGraphBuilder.boothNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
) {
    composable<MainTabRoute.Booth> {
        BoothRoute(
            padding = padding,
            navigateToBoothDetail = navigateToBoothDetail,
        )
    }
}
