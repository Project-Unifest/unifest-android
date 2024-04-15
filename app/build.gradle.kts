@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.unifest.android.application)
    alias(libs.plugins.unifest.android.application.compose)
    alias(libs.plugins.unifest.android.application.firebase)
    alias(libs.plugins.unifest.android.hilt)
    alias(libs.plugins.google.secrets)
}

android {
    namespace = "com.unifest.android"

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
            manifestPlaceholders += mapOf(
                "appName" to "@string/app_name",
            )
        }
    }
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
        projects.feature.waiting,

        libs.androidx.activity.compose,
        libs.androidx.startup,
        libs.timber,
    )
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}
