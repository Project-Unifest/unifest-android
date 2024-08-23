@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.compose.investigator)
}

android {
    namespace = "com.unifest.android.feature.booth"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            "String",
            "UNIFEST_PRIVATE_POLICY_URL",
            properties["UNIFEST_PRIVATE_POLICY_URL"] as String,
        )
        buildConfigField(
            "String",
            "UNIFEST_THIRD_PARTY_POLICY_URL",
            properties["UNIFEST_THIRD_PARTY_POLICY_URL"] as String,
        )
    }
}

dependencies {
    implementations(
        projects.core.data,

        libs.kotlinx.collections.immutable,
        libs.compose.system.ui.controller,
        libs.timber,

        libs.bundles.naver.map.compose,
    )
}
