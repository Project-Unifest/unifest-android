package com.unifest.android.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.unifest.android.feature.booth.navigation.navigateToBoothDetail
import com.unifest.android.feature.booth.navigation.navigateToBoothLocation
import com.unifest.android.feature.home.navigation.HOME_ROUTE
import com.unifest.android.feature.home.navigation.navigateToHome
import com.unifest.android.feature.liked_booth.navigation.navigateToLikedBooth
import com.unifest.android.feature.map.navigation.navigateToMap
import com.unifest.android.feature.menu.navigation.navigateToMenu
import com.unifest.android.feature.stamp.navigation.navigateToStamp
import com.unifest.android.feature.waiting.navigation.navigateToWaiting

internal class MainNavController(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.MAP.route

    //    val currentTab: MainTab?
//        @Composable get() = MainTab.find { tab ->
//            currentDestination?.hasRoute(tab::class) == true
//        }
    val currentTab: MainTab?
        @Composable get() = currentDestination
            ?.route
            ?.let(MainTab.Companion::find)

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.WAITING -> navController.navigateToWaiting(navOptions)
            MainTab.MAP -> navController.navigateToMap(navOptions)
            MainTab.STAMP -> navController.navigateToStamp(navOptions)
            MainTab.MENU -> navController.navigateToMenu(navOptions)
        }
    }

    fun navigateToBoothDetail(boothId: Long) {
        navController.navigateToBoothDetail(boothId)
    }

    fun navigateToBoothLocation() {
        navController.navigateToBoothLocation()
    }

    fun navigateToLikedBooth() {
        navController.navigateToLikedBooth()
    }

    fun navigateToWaiting() {
        navigate(MainTab.WAITING)
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

//    // https://github.com/droidknights/DroidKnights2023_App/pull/243/commits/4bfb6d13908eaaab38ab3a59747d628efa3893cb
//    fun popBackStackIfNotMap() {
//        if (!isSameCurrentDestination<MainTabRoute.Map>()) {
//            popBackStack()
//        }
//    }
    // https://github.com/droidknights/DroidKnights2023_App/pull/243/commits/4bfb6d13908eaaab38ab3a59747d628efa3893cb
    fun popBackStackIfNotMap() {
        if (!isSameCurrentDestination(HOME_ROUTE)) {
            popBackStack()
        }
    }

    fun clearBackStack() {
        val options = NavOptions.Builder()
            .setPopUpTo(navController.graph.findStartDestination().id, inclusive = false)
            .build()
        navController.navigate(startDestination, options)
    }

//    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean {
//        return navController.currentDestination?.hasRoute<T>() == true
//    }
    private fun isSameCurrentDestination(route: String) =
        navController.currentDestination?.route == route

//    @Composable
//    fun shouldShowBottomBar() = MainTab.contains {
//        currentDestination?.hasRoute(it::class) == true
//    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainTab
    }
}

@Composable
internal fun rememberMainNavController(
    navController: NavHostController = rememberNavController(),
): MainNavController = remember(navController) {
    MainNavController(navController)
}
