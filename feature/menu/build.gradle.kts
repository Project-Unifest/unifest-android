@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.menu"
}

dependencies {
    implementations(
        projects.core.data,
        projects.core.domain,

        libs.kotlinx.collections.immutable,
        libs.timber,
    )
}
