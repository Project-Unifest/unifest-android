package com.unifest.android.feature.main

import androidx.compose.runtime.Composable
import com.unifest.android.core.designsystem.R
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.core.navigation.Route

internal enum class MainTab(
    val iconResId: Int,
    val selectedIconResId: Int,
    internal val contentDescription: String,
    val label: String,
    val route: MainTabRoute,
) {
    HOME(
        iconResId = R.drawable.ic_home,
        selectedIconResId = R.drawable.ic_selected_home,
        contentDescription = "Home Icon",
        label = "홈",
        route = MainTabRoute.Home,
    ),
    MAP(
        iconResId = R.drawable.ic_map,
        selectedIconResId = R.drawable.ic_selected_map,
        contentDescription = "Map Icon",
        label = "지도",
        route = MainTabRoute.Map,
    ),

    WAITING(
        iconResId = R.drawable.ic_waiting,
        selectedIconResId = R.drawable.ic_selected_waiting,
        contentDescription = "Waiting Icon",
        label = "웨이팅",
        route = MainTabRoute.Waiting,
    ),

    MENU(
        iconResId = R.drawable.ic_menu,
        selectedIconResId = R.drawable.ic_selected_menu,
        contentDescription = "Menu Icon",
        label = "메뉴",
        route = MainTabRoute.Menu,
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map {it.route}.any { predicate(it) }
        }
    }
}
