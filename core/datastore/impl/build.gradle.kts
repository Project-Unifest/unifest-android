plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.unifest.android.core.datastore.impl"
}

dependencies {
    implementations(
        projects.core.datastore.api,
        projects.core.model,

        libs.androidx.datastore.preferences,
        libs.kotlinx.serialization.json,

        libs.timber,
    )
}
