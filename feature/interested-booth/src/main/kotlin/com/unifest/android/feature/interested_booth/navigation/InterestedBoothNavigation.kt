package com.unifest.android.feature.interested_booth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.feature.interested_booth.InterestedBoothRoute

const val INTERESTED_BOOTH_ROUTE = "interested_booth_route"

fun NavController.navigateToInterestedBooth(navOptions: NavOptions) {
    navigate(INTERESTED_BOOTH_ROUTE, navOptions)
}

fun NavGraphBuilder.interestedBoothNavGraph() {
    composable(route = INTERESTED_BOOTH_ROUTE) {
        InterestedBoothRoute()
    }
}
