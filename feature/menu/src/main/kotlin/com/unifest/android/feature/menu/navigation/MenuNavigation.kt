package com.unifest.android.feature.menu.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.unifest.android.feature.menu.MenuRoute

const val MENU_ROUTE = "menu_route"

fun NavController.navigateToMenu(navOptions: NavOptions) {
    navigate(MENU_ROUTE, navOptions)
}

fun NavGraphBuilder.menuNavGraph(
    padding: PaddingValues,
) {
    composable(route = MENU_ROUTE) {
        MenuRoute(
            padding = padding,
        )
    }
}
