@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.intro"
}

dependencies {
    implementation(project(":core:data"))
    implementations(
        libs.kotlinx.collections.immutable,
        libs.androidx.activity.compose,
        libs.androidx.splash,
        libs.compose.system.ui.controller,
        libs.timber,
    )
}
