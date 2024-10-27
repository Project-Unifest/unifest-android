import com.android.build.api.dsl.ApplicationExtension
import com.unifest.android.convention.Plugins
import com.unifest.android.convention.applyPlugins
import com.unifest.android.convention.configureCompose
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationComposeConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.ANDROID_APPLICATION, Plugins.KOTLIN_COMPOSE)

        extensions.configure<ApplicationExtension> {
            configureCompose(this)
        }
    },
)
