package com.unifest.android.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.core.common.UiText
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.feature.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    onShowSnackBar: (UiText) -> Unit,
) {
    composable<MainTabRoute.Home> {
        HomeRoute(
            padding = padding,
            popBackStack = popBackStack,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
