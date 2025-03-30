@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.unifest.android.core.data.impl"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "APP_VERSION", "\"${libs.versions.versionName.get()}\"")
        buildConfigField("String", "PACKAGE_NAME", "\"${libs.versions.packageName.get()}\"")
    }
}

dependencies {
    implementations(
        projects.core.data.api,
        projects.core.common,
        projects.core.database,
        projects.core.datastore,
        projects.core.model,
        projects.core.network,

        platform(libs.firebase.bom),
        libs.firebase.config,
        libs.firebase.messaging,
        libs.timber,
    )
}
