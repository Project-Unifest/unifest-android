@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.unifest.android.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.unifest.android.core.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }
}

dependencies {
    implementations(
        projects.core.model,

        libs.timber,
    )
    androidTestImplementations(
        libs.junit,
        libs.androidx.junit,
        libs.androidx.test.core,
        libs.androidx.test.runner,
    )
}
