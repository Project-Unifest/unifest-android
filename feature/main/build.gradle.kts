@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.unifest.android.retrofit)
    alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.main"
}

dependencies {
    implementations(
        projects.feature.booth,
        projects.feature.home,
        projects.feature.likedBooth,
        projects.feature.intro,
        projects.feature.map,
        projects.feature.menu,
        projects.feature.waiting,

        libs.androidx.activity.compose,
        libs.kotlinx.collections.immutable,
        libs.coil.compose,
        libs.compose.system.ui.controller,
    )
}
