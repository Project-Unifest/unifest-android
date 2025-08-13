package com.unifest.android.feature.map.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.unifest.android.core.common.UiText
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.core.navigation.Route
import com.unifest.android.feature.map.MapBoothLayoutRoute
import com.unifest.android.feature.map.MapRoute
import com.unifest.android.feature.map.viewmodel.MapViewModel

fun NavController.navigateToMap(navOptions: NavOptions) {
    navigate(MainTabRoute.Map, navOptions)
}

fun NavController.navigateToMapBoothLayout() {
    navigate(Route.MapBoothLayout)
}

fun NavGraphBuilder.mapNavGraph(
    padding: PaddingValues,
    navigateToBoothDetail: (Long) -> Unit,
    navigateToBoothLayout: () -> Unit,
    popBackStack: () -> Unit,
    onShowSnackBar: (UiText) -> Unit,
    getBackStackViewModel: @Composable (NavBackStackEntry) -> MapViewModel,
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
        MapBoothLayoutRoute(
            popBackStack = popBackStack,
            viewModel = getBackStackViewModel(navBackStackEntry),
        )
    }
}
