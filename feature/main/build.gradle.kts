@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.main"
}

dependencies {
    implementations(
        projects.feature.booth,
        projects.feature.festival,
        projects.feature.home,
        projects.feature.likedBooth,
        projects.feature.map,
        projects.feature.menu,
        projects.feature.navigator,
        projects.feature.waiting,
        projects.feature.stamp,

        libs.androidx.activity.compose,
        libs.kotlinx.collections.immutable,
        libs.coil.compose,
        libs.compose.system.ui.controller,
        libs.androidx.navigation.compose,
    )
}
