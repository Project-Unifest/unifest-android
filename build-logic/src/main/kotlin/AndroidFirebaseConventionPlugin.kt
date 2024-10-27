import com.unifest.android.convention.Plugins
import com.unifest.android.convention.applyPlugins
import com.unifest.android.convention.implementation
import com.unifest.android.convention.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFirebaseConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.GOOGLE_SERVICES, Plugins.FIREBASE_CRASHLYTICS)

        dependencies {
            implementation(platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.config)
            implementation(libs.firebase.messaging)
        }
    },
)
