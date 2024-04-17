@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.feature)
}

android {
    namespace = "com.unifest.android.feature.contact"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField(
            "String",
            "CONTACT_WEB_VIEW_URL",
            properties["CONTACT_WEB_VIEW_URL"] as String,
        )
    }
}

dependencies {
    implementations(
        libs.timber,
    )
}
