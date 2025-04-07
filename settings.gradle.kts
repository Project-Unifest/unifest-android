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
        maven("https://repository.map.naver.com/archive/maven")
    }
}

include(
    ":app",
    ":baselineprofile",

    ":core:common",
    ":core:data:api",
    ":core:data:impl",
    ":core:database",
    ":core:datastore",
    ":core:designsystem",
    ":core:model",
    ":core:navigation",
    ":core:network",
    ":core:ui",

    ":feature:booth",
    ":feature:festival",
    ":feature:home",
    ":feature:intro",
    ":feature:liked-booth",
    ":feature:main",
    ":feature:map",
    ":feature:menu",
    ":feature:navigator",
    ":feature:splash",
    ":feature:waiting",
    ":feature:stamp",
)
