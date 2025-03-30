@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.liked_booth"
}

dependencies {
    implementations(
        libs.kotlinx.collections.immutable,
        libs.compose.system.ui.controller,
        libs.timber,
    )
}
