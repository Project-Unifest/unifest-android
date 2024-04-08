@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.intro"
}

dependencies {
    implementations(
        libs.kotlinx.collections.immutable,
        libs.androidx.core,
        libs.androidx.activity.compose,
        libs.androidx.splash,
        libs.system.ui.controller,
        libs.timber,
    )
}
