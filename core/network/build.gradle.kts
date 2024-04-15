@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.retrofit)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.google.secrets)
    id("kotlinx-serialization")
}

android {
    namespace = "com.unifest.android.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementations(
        projects.core.datastore,
        projects.core.model,

        libs.timber,
    )
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}
