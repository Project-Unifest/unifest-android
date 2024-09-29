package com.unifest.android.feature.main

internal enum class MainTab(
    val iconResId: Int,
    val selectedIconResId: Int,
    internal val contentDescription: String,
    val label: String,
    // val route: MainTabRoute,
    val route: String,
) {
    HOME(
        iconResId = R.drawable.ic_home,
        selectedIconResId = R.drawable.ic_selected_home,
        contentDescription = "Home Icon",
        label = "홈",
        // route = MainTabRoute.Home,
        route = "home_route",
    ),
    MAP(
        iconResId = R.drawable.ic_map,
        selectedIconResId = R.drawable.ic_selected_map,
        contentDescription = "Map Icon",
        label = "지도",
        // route = MainTabRoute.Map,
        route = "map_route",
    ),
    WAITING(
        iconResId = R.drawable.ic_waiting,
        selectedIconResId = R.drawable.ic_selected_waiting,
        contentDescription = "Waiting Icon",
        label = "웨이팅",
        // route = MainTabRoute.Waiting,
        route = "waiting_route",
    ),
    //    STAMP(
//        iconResId = R.drawable.ic_stamp,
//        selectedIconResId = R.drawable.ic_selected_stamp,
//        contentDescription = "Stamp Icon",
//        label = "스탬프",
//        route = MainTabRoute.Stamp,
//        route = "stamp_route",
//    ),

    MENU(
        iconResId = R.drawable.ic_menu,
        selectedIconResId = R.drawable.ic_selected_menu,
        contentDescription = "Menu Icon",
        label = "메뉴",
        // route = MainTabRoute.Menu,
        route = "stamp_route",
    ),
    ;

//    companion object {
//        @Composable
//        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
//            return entries.find { predicate(it.route) }
//        }
//
//        @Composable
//        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
//            return entries.map { it.route }.any { predicate(it) }
//        }
//    }
    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainTab? {
            return entries.find { it.route == route }
        }
    }
}
