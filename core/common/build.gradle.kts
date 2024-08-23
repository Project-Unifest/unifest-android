@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.library.compose)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.unifest.android.retrofit)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.unifest.android.core.common"
}

dependencies {
    implementations(
        projects.core.model,

        libs.androidx.hilt.navigation.compose,

        libs.timber,
        libs.bundles.androidx.lifecycle,
    )
}
