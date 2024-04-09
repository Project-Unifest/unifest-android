package com.unifest.android.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.feature.home.HomeRoute

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    onNavigateToIntro: () -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(onNavigateToIntro = onNavigateToIntro,)
    }
}
