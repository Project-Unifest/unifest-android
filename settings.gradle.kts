rootProject.name = "unifest-android"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

include(
    ":app",

    ":core:common",
    ":core:data",
    ":core:designsystem",
    ":core:datastore",
    ":core:domain",
    ":core:network",
    ":core:datastore",
    ":core:ui",

    ":feature:home",
    ":feature:intro",
    ":feature:main",
    ":feature:map",
    ":feature:menu",
    ":feature:navigator",
    ":feature:waiting",
)
