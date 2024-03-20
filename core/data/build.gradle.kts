@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.unifest.android.retrofit)
    id("kotlinx-serialization")
}

android {
    namespace = "com.unifest.android.core.data"
}

dependencies {
    implementations(
        projects.core.network,
        projects.core.datastore,

        libs.timber,
    )
}
