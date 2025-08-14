package com.unifest.android.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object BoothDetail {
        @Serializable
        data class Detail(val boothId: Long) : Route

        @Serializable
        data object Location : Route
    }

    @Serializable
    data object LikeBooth : Route

    @Serializable
    data class HomeCardNews(val imgUrl: String) : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home : MainTabRoute

    @Serializable
    data object Waiting : MainTabRoute

    @Serializable
    data object Map : MainTabRoute

//    @Serializable
//    data object Stamp : MainTabRoute

    @Serializable
    data object Booth : MainTabRoute

    @Serializable
    data object Menu : MainTabRoute
}
