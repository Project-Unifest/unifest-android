@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import java.util.Properties

plugins {
    alias(libs.plugins.unifest.android.application)
    alias(libs.plugins.unifest.android.application.compose)
    alias(libs.plugins.unifest.android.firebase)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.google.secrets)
    alias(libs.plugins.android.application)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.unifest.android"

    signingConfigs {
        getByName("debug") {
            storeFile = file("$rootDir/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }

        create("release") {
            val propertiesFile = rootProject.file("keystore.properties")
            val properties = Properties()
            properties.load(propertiesFile.inputStream())
            storeFile = file(properties["STORE_FILE"] as String)
            storePassword = properties["STORE_PASSWORD"] as String
            keyAlias = properties["KEY_ALIAS"] as String
            keyPassword = properties["KEY_PASSWORD"] as String
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".dev"
            manifestPlaceholders += mapOf(
                "appName" to "@string/app_name_dev",
            )
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders += mapOf(
                "appName" to "@string/app_name",
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.profileinstaller)
    "baselineProfile"(project(":baselineprofile"))
    implementations(
        projects.core.common,
        projects.core.data.api,
        projects.core.data.impl,
        projects.core.database,
        projects.core.datastore.api,
        projects.core.datastore.impl,
        projects.core.designsystem,
        projects.core.model,
        projects.core.navigation,
        projects.core.network,
        projects.core.ui,

        projects.feature.booth,
        projects.feature.festival,
        projects.feature.home,
        projects.feature.intro,
        projects.feature.likedBooth,
        projects.feature.main,
        projects.feature.map,
        projects.feature.menu,
        projects.feature.navigator,
        projects.feature.splash,
        projects.feature.stamp,
        projects.feature.waiting,

        libs.androidx.activity.compose,
        libs.androidx.startup,
        libs.timber,
        libs.coil.compose,
    )
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}
