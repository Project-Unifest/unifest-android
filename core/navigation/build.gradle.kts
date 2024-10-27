plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.unifest.android.core.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
