package com.unifest.android.feature.map.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.unifest.android.core.common.UiText
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.core.navigation.Route
import com.unifest.android.feature.map.MapBoothLayoutRoute
import com.unifest.android.feature.map.MapRoute

fun NavController.navigateToMap(navOptions: NavOptions) {
    navigate(MainTabRoute.Map, navOptions)
}

fun NavController.navigateToMapBoothLayout(imgUrl: String) {
    navigate(Route.MapBoothLayout(imgUrl = imgUrl))
}

fun NavGraphBuilder.mapNavGraph(
    padding: PaddingValues,
    navigateToBoothDetail: (Long) -> Unit,
    navigateToBoothLayout: (String) -> Unit,
    popBackStack: () -> Unit,
    onShowSnackBar: (UiText) -> Unit,
) {
    composable<MainTabRoute.Map> {
        MapRoute(
            padding = padding,
            navigateToBoothDetail = navigateToBoothDetail,
            onShowSnackBar = onShowSnackBar,
            navigateToBoothLayout = navigateToBoothLayout,
        )
    }

    composable<Route.MapBoothLayout> { navBackStackEntry ->
        val imgUrl = navBackStackEntry.toRoute<Route.MapBoothLayout>().imgUrl

        MapBoothLayoutRoute(
            popBackStack = popBackStack,
            imgUrl = imgUrl,
        )
    }
}
