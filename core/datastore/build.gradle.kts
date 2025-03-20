@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.unifest.android.core.datastore"
}

dependencies {
    implementations(
        projects.core.model,

        libs.androidx.datastore.preferences,
        libs.kotlinx.serialization.json,
    )
}
