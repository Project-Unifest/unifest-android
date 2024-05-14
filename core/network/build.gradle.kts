@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.unifest.android.library)
    alias(libs.plugins.unifest.android.retrofit)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.google.secrets)
    id("kotlinx-serialization")
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
localProperties.load(FileInputStream(localPropertiesFile))

android {
    namespace = "com.unifest.android.core.network"

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "SERVER_BASE_URL", getServerBaseUrl("DEBUG_SERVER_BASE_URL"))
        }

        getByName("release") {
            buildConfigField("String", "SERVER_BASE_URL", getServerBaseUrl("RELEASE_SERVER_BASE_URL"))
        }
    }
}

dependencies {
    implementations(
        projects.core.datastore,

        libs.timber,
    )
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}

fun getServerBaseUrl(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}
