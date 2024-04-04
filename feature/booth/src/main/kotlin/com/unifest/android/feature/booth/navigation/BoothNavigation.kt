package com.unifest.android.feature.booth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.feature.booth.BoothRoute

const val Booth_ROUTE = "booth_route"

fun NavController.navigateToBooth(navOptions: NavOptions) {
    navigate(Booth_ROUTE, navOptions)
}

fun NavGraphBuilder.boothNavGraph(
    padding: PaddingValues,
) {
    composable(route = Booth_ROUTE) {
        BoothRoute(
            padding = padding,
        )
    }
}
