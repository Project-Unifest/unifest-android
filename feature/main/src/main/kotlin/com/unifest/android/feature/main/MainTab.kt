package com.unifest.android.feature.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.unifest.android.core.navigation.MainTabRoute
import com.unifest.android.core.navigation.Route

internal enum class MainTab(
    @DrawableRes val iconResId: Int,
    @DrawableRes val selectedIconResId: Int,
    @StringRes val labelResId: Int,
    internal val contentDescription: String,
    val route: MainTabRoute,
) {
    HOME(
        iconResId = R.drawable.ic_home,
        selectedIconResId = R.drawable.ic_selected_home,
        labelResId = R.string.home_label,
        contentDescription = "Home Icon",
        route = MainTabRoute.Home,
    ),
    WAITING(
        iconResId = R.drawable.ic_waiting,
        selectedIconResId = R.drawable.ic_selected_waiting,
        labelResId = R.string.waiting_label,
        contentDescription = "Waiting Icon",
        route = MainTabRoute.Waiting,
    ),
    MAP(
        iconResId = R.drawable.ic_map,
        selectedIconResId = R.drawable.ic_selected_map,
        labelResId = R.string.map_label,
        contentDescription = "Map Icon",
        route = MainTabRoute.Map,
    ),

//    STAMP(
//        iconResId = R.drawable.ic_stamp,
//        selectedIconResId = R.drawable.ic_selected_stamp,
//        contentDescription = "Stamp Icon",
//        label = "스탬프",
//        route = MainTabRoute.Stamp,
//    ),
    BOOTH(
        iconResId = R.drawable.ic_booth,
        selectedIconResId = R.drawable.ic_selected_booth,
        labelResId = R.string.booth_label,
        contentDescription = "Booth Icon",
        route = MainTabRoute.Booth,
    ),
    MENU(
        iconResId = R.drawable.ic_menu,
        selectedIconResId = R.drawable.ic_selected_menu,
        labelResId = R.string.menu_label,
        contentDescription = "Menu Icon",
        route = MainTabRoute.Menu,
    ),
    ;

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
