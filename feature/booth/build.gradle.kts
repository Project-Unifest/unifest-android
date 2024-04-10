@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.booth"
}

dependencies {
    implementations(
        libs.kotlinx.collections.immutable,
        libs.androidx.core,
        libs.timber,
        libs.calendar.compose,
        libs.compose.system.ui.controller,
        libs.naver.map.sdk,
        libs.naver.map.compose,
        libs.naver.map.location,
    )
}
