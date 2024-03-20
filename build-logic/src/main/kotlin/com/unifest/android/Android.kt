package com.unifest.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun Project.configureAndroid(extension: CommonExtension<*, *, *, *, *>) {
    extension.apply {
        compileSdk = ApplicationConfig.CompileSdk

        defaultConfig {
            minSdk = ApplicationConfig.MinSdk
        }

        compileOptions {
            sourceCompatibility = ApplicationConfig.JavaVersion
            targetCompatibility = ApplicationConfig.JavaVersion
            isCoreLibraryDesugaringEnabled = true
        }

        extensions.configure<KotlinProjectExtension> {
            jvmToolchain(ApplicationConfig.JavaVersionAsInt)
        }

        dependencies {
            coreLibraryDesugaring(libs.desugar.jdk.libs)
            detektPlugins(libs.detekt.formatting)
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
        }
    }
}
