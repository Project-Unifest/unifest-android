plugins {
    alias(libs.plugins.unifest.jvm.kotlin)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    compileOnly(
        libs.compose.stable.marker,
    )
    implementation(libs.kotlinx.serialization.json)
}
