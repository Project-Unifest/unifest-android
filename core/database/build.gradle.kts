@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.unifest.android.room)
    id("kotlinx-serialization")
}

android {
    namespace = "com.unifest.android.core.database"
}

dependencies {
    implementations(
        libs.timber,
    )
}
