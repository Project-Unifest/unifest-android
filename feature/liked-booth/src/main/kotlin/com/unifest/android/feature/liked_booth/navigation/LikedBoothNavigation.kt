package com.unifest.android.feature.liked_booth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unifest.android.core.common.UiText
import com.unifest.android.feature.liked_booth.LikedBoothRoute

const val LIKE_BOOTH_ROUTE = "like_booth_route"

fun NavController.navigateToLikedBooth() {
    // navigate(Route.LikeBooth)
    navigate(LIKE_BOOTH_ROUTE)
}

fun NavGraphBuilder.likedBoothNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
    onShowSnackBar: (UiText) -> Unit,
) {
    // composable<Route.LikeBooth> {
    composable(route = LIKE_BOOTH_ROUTE) {
        LikedBoothRoute(
            padding = padding,
            popBackStack = popBackStack,
            navigateToBoothDetail = navigateToBoothDetail,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
