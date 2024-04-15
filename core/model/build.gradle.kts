
plugins {
    alias(libs.plugins.unifest.android.library)
    id("kotlinx-serialization")
}

android {
    namespace = "com.unifest.android.core.model"
}

dependencies {
    compileOnly(
        libs.compose.stable.marker,
    )
    implementation(libs.kotlinx.serialization.json)
}
