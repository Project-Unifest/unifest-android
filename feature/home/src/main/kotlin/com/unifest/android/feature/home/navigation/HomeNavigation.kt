package com.unifest.android.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.unifest.android.core.common.UiText
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.core.navigation.Route
import com.unifest.android.feature.home.HomeCardNewsRoute
import com.unifest.android.feature.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(MainTabRoute.Home, navOptions)
}

fun NavController.navigateToHomeCardNews(imgUrl: String) {
    navigate(Route.HomeCardNews(imgUrl))
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateToHomeCardNews: (String) -> Unit,
    onShowSnackBar: (UiText) -> Unit,
) {
    composable<MainTabRoute.Home> {
        HomeRoute(
            padding = padding,
            popBackStack = popBackStack,
            onShowSnackBar = onShowSnackBar,
            navigateToHomeCardNews = navigateToHomeCardNews,
        )
    }
    composable<Route.HomeCardNews> { navBackStackEntry ->
        val imageUrl = navBackStackEntry.toRoute<Route.HomeCardNews>().imgUrl
        HomeCardNewsRoute(
            popBackStack = popBackStack,
            imageUrl = imageUrl,
        )
    }
}
