@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.stamp"
}

dependencies {
    implementations(
        projects.core.data.api,

        libs.kotlinx.collections.immutable,
        libs.coil.compose,
        libs.zxing.android.embedded,
        libs.timber,
        libs.compose.unstyled,
    )
}
