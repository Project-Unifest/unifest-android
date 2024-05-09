@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    // alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.waiting"
}

dependencies {
    implementations(
        libs.kotlinx.collections.immutable,
        libs.timber,
    )
}
