package com.unifest.android

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureCompose(extension: CommonExtension<*, *, *, *, *>) {
    extension.apply {
        dependencies {
            implementation(libs.bundles.androidx.compose)
            debugImplementation(libs.androidx.compose.ui.tooling)
        }

        configure<ComposeCompilerGradlePluginExtension> {
            enableStrongSkippingMode.set(true)
            includeSourceInformation.set(true)

            metricsDestination.file("build/composeMetrics")
            reportsDestination.file("build/composeReports")

            stabilityConfigurationFile.set(project.rootDir.resolve("stability.config.conf"))
        }
    }
}

//internal fun Project.configureCompose(extension: CommonExtension<*, *, *, *, *>) {
//    extension.apply {
//        buildFeatures {
//            compose = true
//        }
//
//        composeOptions {
//            kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
//        }
//
//        dependencies {
//            implementation(libs.androidx.compose.bom)
//            androidTestImplementation(libs.androidx.compose.bom)
//            implementation(libs.androidx.compose.material.iconsExtended)
//            implementation(libs.androidx.compose.material3)
//            implementation(libs.androidx.compose.ui)
//            implementation(libs.androidx.compose.ui.tooling.preview)
//            debugImplementation(libs.androidx.compose.ui.tooling)
//        }
//    }
//
//    tasks.withType<KotlinCompile>().configureEach {
//        kotlinOptions {
//            freeCompilerArgs = freeCompilerArgs + listOf(
//                "-P",
//                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$rootDir/report/compose-metrics",
//            )
//            freeCompilerArgs = freeCompilerArgs + listOf(
//                "-P",
//                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$rootDir/report/compose-reports",
//            )
//        }
//    }
//}
