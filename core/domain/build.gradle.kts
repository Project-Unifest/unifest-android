@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
}

android {
    namespace = "com.nexters.ilab.android.core.designsystem"
}

dependencies {
    compileOnly(
        libs.compose.stable.marker,
    )
    implementations(
        libs.androidx.core,
    )
}
