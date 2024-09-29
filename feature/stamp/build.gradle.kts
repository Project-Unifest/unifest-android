@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.stamp"

    android {
        buildTypes {
            debug {
                isMinifyEnabled = false
            }
            release {
                isMinifyEnabled = false
            }
        }
    }
}

dependencies {
    implementations(
        projects.core.data,

        libs.kotlinx.collections.immutable,
        libs.coil.compose,
        libs.zxing.android.embedded,
        libs.timber,
    )
}
