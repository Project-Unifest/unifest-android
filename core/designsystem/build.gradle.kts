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

        libs.androidx.splash,
        libs.coil.compose,
        libs.timber,
        libs.ballon.compose,
        libs.compose.keyboard.state,
        libs.compose.effects,

        libs.bundles.naver.map.compose,
        libs.bundles.landscapist,
    )
}
