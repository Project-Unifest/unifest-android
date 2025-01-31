package com.unifest.android.feature.menu.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.core.common.UiText
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.feature.menu.MenuRoute

fun NavController.navigateToMenu(navOptions: NavOptions) {
    navigate(MainTabRoute.Menu, navOptions)
}

fun NavGraphBuilder.menuNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToLikedBooth: () -> Unit,
    navigateToBoothDetail: (Long) -> Unit,
    onShowSnackBar: (UiText) -> Unit,
) {
    composable<MainTabRoute.Menu> {
        MenuRoute(
            padding = padding,
            popBackStack = popBackStack,
            navigateToLikedBooth = navigateToLikedBooth,
            navigateToBoothDetail = navigateToBoothDetail,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
