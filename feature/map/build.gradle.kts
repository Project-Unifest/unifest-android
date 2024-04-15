@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.compose.investigator)
    id("kotlin-parcelize")
}

android {
    namespace = "com.unifest.android.feature.map"
}

dependencies {
    implementations(
        projects.core.data,

        libs.kotlinx.collections.immutable,
        libs.timber,
        libs.compose.system.ui.controller,

        libs.bundles.naver.map.compose,
    )
}
