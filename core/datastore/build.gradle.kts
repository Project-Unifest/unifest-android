@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.unifest.android.core.datastore"
}

dependencies {
    implementations(
        libs.androidx.core,
        libs.androidx.datastore.preferences,
        libs.kotlinx.serialization.json,
    )
}
