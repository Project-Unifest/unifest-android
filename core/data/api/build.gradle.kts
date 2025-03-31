@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.library)
}

android {
    namespace = "com.unifest.android.core.data.api"
}

dependencies {
    implementations(
        projects.core.model,

        platform(libs.firebase.bom),
        libs.firebase.config,
        libs.timber,
    )
}
