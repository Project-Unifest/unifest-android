package com.unifest.android.feature.liked_booth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.unifest.android.core.common.UiText
import com.unifest.android.core.navigation.Route
import com.unifest.android.feature.liked_booth.LikedBoothRoute

fun NavController.navigateToLikedBooth() {
    navigate(Route.LikeBooth)
}

fun NavGraphBuilder.likedBoothNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
    onShowSnackBar: (UiText) -> Unit,
) {
    composable<Route.LikeBooth> {
        LikedBoothRoute(
            padding = padding,
            popBackStack = popBackStack,
            navigateToBoothDetail = navigateToBoothDetail,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
