package com.unifest.android.feature.interested_booth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unifest.android.feature.interested_booth.InterestedBoothRoute

const val INTERESTED_BOOTH_ROUTE = "interested_booth_route"

fun NavController.navigateToInterestedBooth() {
    navigate(INTERESTED_BOOTH_ROUTE)
}

fun NavGraphBuilder.interestedBoothNavGraph(padding: PaddingValues) {
    composable(route = INTERESTED_BOOTH_ROUTE) {
        InterestedBoothRoute(padding = padding)
    }
}
