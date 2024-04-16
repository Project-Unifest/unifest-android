@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.unifest.android.core.data"
}

dependencies {
    implementations(
        projects.core.database,
        projects.core.datastore,
        projects.core.model,
        projects.core.network,

        libs.timber,
    )
}
