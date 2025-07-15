@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.unifest.android.feature.booth"
}

dependencies {
    implementations(
        libs.kotlinx.collections.immutable,
        libs.timber,
    )
}
