plugins {
    alias(libs.plugins.unifest.android.library)
}

android {
    namespace = "com.unifest.android.core.datastore.api"
}

dependencies {
    implementations(
        projects.core.model,

        libs.kotlinx.coroutines.core,
    )
}
