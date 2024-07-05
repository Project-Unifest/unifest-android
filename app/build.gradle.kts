@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import java.util.Properties

plugins {
    alias(libs.plugins.unifest.android.application)
    alias(libs.plugins.unifest.android.application.compose)
    alias(libs.plugins.unifest.android.firebase)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.google.secrets)
}

android {
    namespace = "com.unifest.android"

    signingConfigs {
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

composeCompiler {
    enableStrongSkippingMode = true
    stabilityConfigurationFile = project.rootDir.resolve("stability.config.conf")
}

dependencies {
    implementations(
        projects.core.common,
        projects.core.data,
        projects.core.designsystem,
        projects.core.network,
        projects.core.datastore,
        projects.core.ui,

        projects.feature.home,
        projects.feature.intro,
        projects.feature.main,
        projects.feature.map,
        projects.feature.menu,
        projects.feature.splash,
        projects.feature.waiting,

        libs.androidx.activity.compose,
        libs.androidx.startup,
        libs.timber,
    )
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}
