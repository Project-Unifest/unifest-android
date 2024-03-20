@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
}

android {
    namespace = "com.nexters.ilab.android.core.designsystem"
}

dependencies {
    implementations(
        libs.androidx.core,
    )
}
