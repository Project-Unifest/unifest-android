@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.library.compose)
}

android {
    namespace = "com.unifest.android.core.designsystem"
}

dependencies {
    implementations(
        projects.core.common,

        libs.androidx.core,
        libs.coil.compose,
        libs.timber,

        libs.bundles.landscapist,
    )
}
