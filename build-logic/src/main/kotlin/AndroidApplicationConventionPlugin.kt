import com.android.build.api.dsl.ApplicationExtension
import com.unifest.android.ApplicationConfig
import com.unifest.android.Plugins
import com.unifest.android.applyPlugins
import com.unifest.android.configureAndroid
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.AndroidApplication, Plugins.KotlinAndroid)

        extensions.configure<ApplicationExtension> {
            configureAndroid(this)

            defaultConfig {
                targetSdk = ApplicationConfig.TargetSdk
                versionCode = ApplicationConfig.VersionCode
                versionName = ApplicationConfig.VersionName
            }
        }
    },
)
