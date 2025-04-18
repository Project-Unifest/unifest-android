@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.home"
}

dependencies {
    implementations(
        projects.feature.festival,

        libs.kotlinx.collections.immutable,
        libs.timber,
        libs.calendar.compose,
    )
}
