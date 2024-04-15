plugins {
    alias(libs.plugins.unifest.jvm.kotlin)
    id("kotlinx-serialization")
}

dependencies {
    compileOnly(
        libs.compose.stable.marker,
    )
    implementation(libs.kotlinx.serialization.json)
}
