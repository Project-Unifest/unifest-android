@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.menu"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            "String",
            "UNIFEST_CONTACT_URL",
            properties["UNIFEST_CONTACT_URL"] as String,
        )
        buildConfigField(
            "String",
            "UNIFEST_WEB_URL",
            properties["UNIFEST_WEB_URL"] as String,
        )
    }
}

dependencies {
    implementations(
        projects.core.data,
        projects.feature.festival,

        libs.kotlinx.collections.immutable,
        libs.timber,
    )
}
