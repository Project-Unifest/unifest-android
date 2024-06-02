package com.unifest.android.feature.main

import com.unifest.android.core.designsystem.R

internal enum class MainTab(
    val iconResId: Int,
    val selectedIconResId: Int,
    val contentDescription: String,
    val label: String,
    val route: String,
) {
    HOME(
        iconResId = R.drawable.ic_home,
        selectedIconResId = R.drawable.ic_selected_home,
        contentDescription = "Home Icon",
        label = "홈",
        route = "home_route",
    ),
    MAP(
        iconResId = R.drawable.ic_map,
        selectedIconResId = R.drawable.ic_selected_map,
        contentDescription = "Map Icon",
        label = "지도",
        route = "map_route",
    ),

    WAITING(
        iconResId = R.drawable.ic_waiting,
        selectedIconResId = R.drawable.ic_selected_waiting,
        contentDescription = "Waiting Icon",
        label = "웨이팅",
        route = "waiting_route",
    ),

    MENU(
        iconResId = R.drawable.ic_menu,
        selectedIconResId = R.drawable.ic_selected_menu,
        contentDescription = "Menu Icon",
        label = "메뉴",
        route = "menu_route",
    ),
    ;

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainTab? {
            return entries.find { it.route == route }
        }
    }
}
